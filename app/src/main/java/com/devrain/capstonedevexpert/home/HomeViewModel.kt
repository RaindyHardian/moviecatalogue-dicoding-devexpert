package com.devrain.capstonedevexpert.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devrain.capstonedevexpert.core.domain.usecase.MovieUseCase

class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val movie = movieUseCase.getAllMovie().asLiveData()

}
