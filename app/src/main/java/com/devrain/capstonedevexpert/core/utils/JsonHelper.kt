package com.devrain.capstonedevexpert.core.utils

import android.content.Context
import com.devrain.capstonedevexpert.R
import com.devrain.capstonedevexpert.core.data.source.remote.response.MovieResponse
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(R.raw.movie).bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun loadData(): List<MovieResponse> {
        val list = ArrayList<MovieResponse>()
        val responseObject = JSONObject(parsingFileToString().toString())
        val listArray = responseObject.getJSONArray("results")
        for (i in 0 until listArray.length()) {
            val course = listArray.getJSONObject(i)

            val id = course.getString("id")
            val title = course.getString("title")
            val overview = course.getString("overview")
            val releaseDate = course.getString("release_date")
            val posterPath = course.getString("poster_path")

            val movieResponse = MovieResponse(
                id = id,
                title = title,
                overview = overview,
                releaseDate = releaseDate,
                posterPath = posterPath
            )
            list.add(movieResponse)
        }
        return list
    }
}