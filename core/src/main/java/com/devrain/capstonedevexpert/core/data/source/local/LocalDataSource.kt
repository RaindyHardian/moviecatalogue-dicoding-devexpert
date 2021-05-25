package com.devrain.capstonedevexpert.core.data.source.local

import androidx.lifecycle.LiveData
import com.devrain.capstonedevexpert.core.data.source.local.entity.MovieEntity
import com.devrain.capstonedevexpert.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: com.devrain.capstonedevexpert.core.data.source.local.room.MovieDao) {

//    companion object {
//        private var instance: LocalDataSource? = null
//
//        fun getInstance(movieDao: MovieDao): LocalDataSource =
//            instance ?: synchronized(this) {
//                instance ?: LocalDataSource(movieDao)
//            }
//    }

    fun getAllMovie(): Flow<List<com.devrain.capstonedevexpert.core.data.source.local.entity.MovieEntity>> = movieDao.getAllMovie()

    fun getFavoriteMovie(): Flow<List<com.devrain.capstonedevexpert.core.data.source.local.entity.MovieEntity>> = movieDao.getFavoriteMovie()

    suspend fun insertMovie(movie: List<com.devrain.capstonedevexpert.core.data.source.local.entity.MovieEntity>) = movieDao.insertMovie(movie)

    fun setFavoriteMovie(movie: com.devrain.capstonedevexpert.core.data.source.local.entity.MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}