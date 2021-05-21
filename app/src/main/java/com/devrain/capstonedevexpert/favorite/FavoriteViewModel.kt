package com.devrain.capstonedevexpert.favorite

import androidx.lifecycle.ViewModel
import com.devrain.capstonedevexpert.core.data.source.MovieRepository
import com.devrain.capstonedevexpert.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val favoriteMovie = movieUseCase.getFavoriteMovie()

}