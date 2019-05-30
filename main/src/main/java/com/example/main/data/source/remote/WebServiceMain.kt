package com.example.main.data.source.remote


import com.example.core.BuildConfig
import com.example.core.data.model.movie.Movie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WebServiceMain {

    @GET("popular?api_key=$API_KEY")
    fun getMoviePopular(@Query("page") page: Int?): Call<Movie>

    @GET("top_rated?api_key=$API_KEY")
    fun getMovieTopRated(@Query("page") page: Int?): Call<Movie>

    companion object {

        const val API_KEY = BuildConfig.API_KEY_MOVIE_DB
    }
}
