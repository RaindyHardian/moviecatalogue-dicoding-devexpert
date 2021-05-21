package com.devrain.capstonedevexpert.core.di

import android.content.Context
import com.devrain.capstonedevexpert.core.data.source.MovieRepository
import com.devrain.capstonedevexpert.core.data.source.local.LocalDataSource
import com.devrain.capstonedevexpert.core.data.source.local.entity.MovieDatabase
import com.devrain.capstonedevexpert.core.data.source.remote.RemoteDataSource
import com.devrain.capstonedevexpert.core.domain.repository.IMovieRepository
import com.devrain.capstonedevexpert.core.domain.usecase.MovieInteractor
import com.devrain.capstonedevexpert.core.domain.usecase.MovieUseCase
import com.devrain.capstonedevexpert.core.utils.AppExecutors
import com.devrain.capstonedevexpert.core.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): IMovieRepository {
        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideMovieUseCase(context: Context): MovieUseCase {
        val repository = provideRepository(context)
        return MovieInteractor(repository)
    }
}