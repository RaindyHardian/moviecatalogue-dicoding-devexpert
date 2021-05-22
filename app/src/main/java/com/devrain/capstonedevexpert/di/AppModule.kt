package com.devrain.capstonedevexpert.di

import com.devrain.capstonedevexpert.core.domain.usecase.MovieInteractor
import com.devrain.capstonedevexpert.core.domain.usecase.MovieUseCase
import com.devrain.capstonedevexpert.detail.DetailMovieViewModel
import com.devrain.capstonedevexpert.favorite.FavoriteViewModel
import com.devrain.capstonedevexpert.home.HomeViewModel
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
}