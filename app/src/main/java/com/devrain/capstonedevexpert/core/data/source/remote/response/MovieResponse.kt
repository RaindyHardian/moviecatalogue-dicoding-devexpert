package com.devrain.capstonedevexpert.core.data.source.remote.response

data class MovieResponse(
    val id: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String
)