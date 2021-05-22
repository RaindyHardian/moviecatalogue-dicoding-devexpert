package com.devrain.capstonedevexpert.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.devrain.capstonedevexpert.core.data.source.local.LocalDataSource
import com.devrain.capstonedevexpert.core.data.source.local.entity.MovieEntity
import com.devrain.capstonedevexpert.core.data.source.remote.RemoteDataSource
import com.devrain.capstonedevexpert.core.data.source.remote.network.ApiResponse
import com.devrain.capstonedevexpert.core.data.source.remote.response.MovieResponse
import com.devrain.capstonedevexpert.core.domain.model.Movie
import com.devrain.capstonedevexpert.core.domain.repository.IMovieRepository
import com.devrain.capstonedevexpert.core.utils.AppExecutors
import com.devrain.capstonedevexpert.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

//    companion object {
//        @Volatile
//        private var instance: MovieRepository? = null
//
//        fun getInstance(
//            remoteData: RemoteDataSource,
//            localData: LocalDataSource,
//            appExecutors: AppExecutors
//        ): MovieRepository =
//            instance ?: synchronized(this) {
//                instance ?: MovieRepository(remoteData, localData, appExecutors)
//            }
//    }

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
//                return Transformations.map(localDataSource.getAllMovie()) {
//                    DataMapper.mapEntitiesToDomain(it)
//                }
                return localDataSource.getAllMovie().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> {
//        return Transformations.map(localDataSource.getFavoriteMovie()) {
//            DataMapper.mapEntitiesToDomain(it)
//        }
        return localDataSource.getFavoriteMovie().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
//        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }
}

