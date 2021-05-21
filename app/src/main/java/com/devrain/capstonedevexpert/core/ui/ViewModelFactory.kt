package com.devrain.capstonedevexpert.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devrain.capstonedevexpert.core.data.source.MovieRepository
import com.devrain.capstonedevexpert.core.di.Injection
import com.devrain.capstonedevexpert.core.domain.usecase.MovieUseCase
import com.devrain.capstonedevexpert.detail.DetailMovieViewModel
import com.devrain.capstonedevexpert.favorite.FavoriteViewModel
import com.devrain.capstonedevexpert.home.HomeViewModel

class ViewModelFactory private constructor(private val movieUseCase: MovieUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance
                        ?: ViewModelFactory(
//                            Injection.provideRepository(
//                                context
//                            )
                        Injection.provideMovieUseCase(context)
                        )
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(movieUseCase) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(movieUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(movieUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}