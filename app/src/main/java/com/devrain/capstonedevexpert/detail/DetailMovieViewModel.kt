package com.devrain.capstonedevexpert.detail

import androidx.lifecycle.ViewModel
import com.devrain.capstonedevexpert.core.data.source.MovieRepository
import com.devrain.capstonedevexpert.core.data.source.local.entity.MovieEntity
import com.devrain.capstonedevexpert.core.domain.model.Movie
import com.devrain.capstonedevexpert.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) =
        movieUseCase.setFavoriteMovie(movie, newStatus)
}