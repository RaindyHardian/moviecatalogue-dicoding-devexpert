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

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMovie(): LiveData<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Movie>> {
//                return localDataSource.getAllMovie()
                return Transformations.map(localDataSource.getAllMovie()) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asLiveData()

    override fun getFavoriteMovie(): LiveData<List<Movie>> {
//        return localDataSource.getFavoriteMovie()
        return Transformations.map(localDataSource.getFavoriteMovie()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
//        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }
}

