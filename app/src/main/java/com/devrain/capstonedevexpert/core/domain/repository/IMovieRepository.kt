package com.devrain.capstonedevexpert.core.domain.repository

import androidx.lifecycle.LiveData
import com.devrain.capstonedevexpert.core.data.source.Resource
import com.devrain.capstonedevexpert.core.domain.model.Movie

interface IMovieRepository {
    fun getAllMovie(): LiveData<Resource<List<Movie>>>

    fun getFavoriteMovie(): LiveData<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)
}