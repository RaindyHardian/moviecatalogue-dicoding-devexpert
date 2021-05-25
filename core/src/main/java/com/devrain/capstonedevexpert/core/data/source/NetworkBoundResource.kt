package com.devrain.capstonedevexpert.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.devrain.capstonedevexpert.core.data.source.remote.network.ApiResponse
import com.devrain.capstonedevexpert.core.utils.AppExecutors
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<com.devrain.capstonedevexpert.core.data.source.Resource<ResultType>> = flow {
        emit(com.devrain.capstonedevexpert.core.data.source.Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(com.devrain.capstonedevexpert.core.data.source.Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map {
                        com.devrain.capstonedevexpert.core.data.source.Resource.Success(
                            it
                        )
                    })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map {
                        com.devrain.capstonedevexpert.core.data.source.Resource.Success(
                            it
                        )
                    })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(
                        com.devrain.capstonedevexpert.core.data.source.Resource.Error<ResultType>(
                            apiResponse.errorMessage
                        )
                    )
                }
            }
        } else {
            emitAll(loadFromDB().map {
                com.devrain.capstonedevexpert.core.data.source.Resource.Success(
                    it
                )
            })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<com.devrain.capstonedevexpert.core.data.source.Resource<ResultType>> = result
}