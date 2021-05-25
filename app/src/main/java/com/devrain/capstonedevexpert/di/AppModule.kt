package com.devrain.capstonedevexpert.di

import com.devrain.capstonedevexpert.core.domain.usecase.MovieInteractor
import com.devrain.capstonedevexpert.core.domain.usecase.MovieUseCase
import com.devrain.capstonedevexpert.detail.DetailMovieViewModel
import com.devrain.capstonedevexpert.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
}