package com.example.main.ui

import android.arch.lifecycle.ViewModel

import com.example.core.data.model.Resource
import com.example.core.data.model.movie.Movie
import com.example.core.util.SingleLiveEvent
import com.example.main.data.source.remote.MainRepository

class MainViewModel : ViewModel() {

    var movieSingleLiveEvent = SingleLiveEvent<Resource<Movie>>()
    private val mainRepository = MainRepository()

    fun getListMovies(page: Int) {
        mainRepository.getMovies(movieSingleLiveEvent, page)
    }

    fun getListMoviesTop(page: Int) {
        mainRepository.getTopMovies(movieSingleLiveEvent, page)
    }
}
