package com.devrain.capstonedevexpert.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    val movieId: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val isFavorite: Boolean
) : Parcelable