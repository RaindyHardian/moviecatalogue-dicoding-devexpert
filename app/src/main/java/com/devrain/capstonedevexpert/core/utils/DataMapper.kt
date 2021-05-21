package com.devrain.capstonedevexpert.core.utils

import com.devrain.capstonedevexpert.core.data.source.local.entity.MovieEntity
import com.devrain.capstonedevexpert.core.data.source.remote.response.MovieResponse
import com.devrain.capstonedevexpert.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val tourism = MovieEntity(
                movieId = it.id,
                title = it.title,
                overview = it.overview,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                isFavorite = false
            )
            movieList.add(tourism)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                movieId = it.movieId,
                title = it.title,
                overview = it.overview,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                isFavorite = it.isFavorite
            )
        }
    fun mapDomainToEntity(input: Movie) = MovieEntity(
        movieId = input.movieId,
        title = input.title,
        overview = input.overview,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        isFavorite = input.isFavorite
    )
}