package com.devrain.capstonedevexpert.core.domain.usecase

import androidx.lifecycle.LiveData
import com.devrain.capstonedevexpert.core.data.source.Resource
import com.devrain.capstonedevexpert.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}