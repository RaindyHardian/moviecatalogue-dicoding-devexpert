package com.devrain.capstonedevexpert.core.data.source.remote

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devrain.capstonedevexpert.core.data.source.remote.network.ApiResponse
import com.devrain.capstonedevexpert.core.data.source.remote.network.ApiService
import com.devrain.capstonedevexpert.core.data.source.remote.response.ListMovieResponse
import com.devrain.capstonedevexpert.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val apiService: ApiService) {
//    companion object {
//        @Volatile
//        private var instance: RemoteDataSource? = null
//
//        fun getInstance(service: ApiService): RemoteDataSource =
//            instance ?: synchronized(this) {
//                instance ?: RemoteDataSource(service)
//            }
//    }

    fun getAllMovie(): Flow<ApiResponse<List<MovieResponse>>> {
//        val resultData = MutableLiveData<ApiResponse<List<MovieResponse>>>()

        //get data from remote api
//        val client = apiService.getList()
//        client.enqueue(object : Callback<ListMovieResponse> {
//            override fun onResponse(
//                call: Call<ListMovieResponse>,
//                response: Response<ListMovieResponse>
//            ) {
//                val dataArray = response.body()?.results
//                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
//            }
//            override fun onFailure(call: Call<ListMovieResponse>, t: Throwable) {
//                resultData.value = ApiResponse.Error(t.message.toString())
//                Log.e("RemoteDataSource", t.message.toString())
//            }
//        })
//
//        return resultData
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}
