package com.example.main.data.source.remote

import com.example.core.data.model.Resource
import com.example.core.data.model.movie.Movie
import com.example.core.util.SingleLiveEvent

interface DataSourceMainRepository {

    fun getMovies(movieSingleLiveEvent: SingleLiveEvent<Resource<Movie>>, page: Int)
    fun getTopMovies(movieTopSingleLiveEvent: SingleLiveEvent<Resource<Movie>>, page: Int)
}
