package com.devrain.capstonedevexpert.core.data.source.remote.network

import com.devrain.capstonedevexpert.core.BuildConfig
import com.devrain.capstonedevexpert.core.data.source.remote.response.ListMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/week")
    suspend fun getList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ListMovieResponse
}
