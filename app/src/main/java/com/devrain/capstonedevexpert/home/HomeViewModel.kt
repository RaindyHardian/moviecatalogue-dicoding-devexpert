package com.devrain.capstonedevexpert.home

import androidx.lifecycle.ViewModel
import com.devrain.capstonedevexpert.core.data.source.MovieRepository
import com.devrain.capstonedevexpert.core.domain.usecase.MovieUseCase

class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val movie = movieUseCase.getAllMovie()

}
