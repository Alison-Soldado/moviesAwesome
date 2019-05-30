package com.example.main.data.source.remote


import com.example.core.data.model.Resource
import com.example.core.data.model.movie.Movie
import com.example.core.util.SingleLiveEvent

import java.util.Objects

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository : DataSourceMainRepository {

    private val webService = ApiUtilsMain().listMovieService

    override fun getMovies(movieSingleLiveEvent: SingleLiveEvent<Resource<Movie>>, page: Int) {
        webService.getMoviePopular(page).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                movieSingleLiveEvent.value =
                    Resource.success(Objects.requireNonNull<Movie>(response.body()))
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                movieSingleLiveEvent.value = Resource.error(t.message, null)
            }
        })
    }

    override fun getTopMovies(
        movieTopSingleLiveEvent: SingleLiveEvent<Resource<Movie>>,
        page: Int
    ) {
        webService.getMovieTopRated(page).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                movieTopSingleLiveEvent.value =
                    Resource.success(Objects.requireNonNull<Movie>(response.body()))
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                movieTopSingleLiveEvent.value = Resource.error(t.message, null)
            }
        })
    }
}
