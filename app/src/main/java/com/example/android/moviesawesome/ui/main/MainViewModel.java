package com.example.android.moviesawesome.ui.main;

import android.arch.lifecycle.ViewModel;

import com.example.android.moviesawesome.data.model.Movie;
import com.example.android.moviesawesome.data.source.remote.main.MainRepository;
import com.example.android.moviesawesome.util.SingleLiveEvent;

public class MainViewModel extends ViewModel {

    SingleLiveEvent<Movie> movieSingleLiveEvent = new SingleLiveEvent<>();
    private MainRepository mainRepository = new MainRepository();

    void getListMovies(Integer page) {
        mainRepository.getMovie(movieSingleLiveEvent, page);
    }

    void getListMoviesTop(Integer page) {
        mainRepository.getMovieTop(movieSingleLiveEvent, page);
    }
}
