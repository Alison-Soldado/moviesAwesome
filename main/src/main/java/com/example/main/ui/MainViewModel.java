package com.example.main.ui;

import android.arch.lifecycle.ViewModel;

import com.example.core.data.model.Resource;
import com.example.core.data.model.movie.Movie;
import com.example.core.util.SingleLiveEvent;
import com.example.main.data.source.remote.MainRepository;

public class MainViewModel extends ViewModel {

    SingleLiveEvent<Resource<Movie>> movieSingleLiveEvent = new SingleLiveEvent<>();
    private MainRepository mainRepository = new MainRepository();

    public void getListMovies(Integer page) {
        mainRepository.getMovies(movieSingleLiveEvent, page);
    }

    public void getListMoviesTop(Integer page) {
        mainRepository.getTopMovies(movieSingleLiveEvent, page);
    }
}
