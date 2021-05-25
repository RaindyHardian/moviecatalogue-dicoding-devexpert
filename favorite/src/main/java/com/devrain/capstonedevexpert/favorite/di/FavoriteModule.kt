package com.devrain.capstonedevexpert.favorite.di

import com.devrain.capstonedevexpert.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module{
    viewModel { FavoriteViewModel(get()) }
}