package com.example.android.moviesawesome.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.moviesawesome.data.model.Movie;
import com.example.android.moviesawesome.data.source.remote.main.MainRepository;

public class MainViewModel extends ViewModel {

    MutableLiveData<Movie> movieMutableLiveData = new MutableLiveData<>();
    private MainRepository mainRepository = new MainRepository();

    void getListMovies(Integer page) {
        mainRepository.getMovie(movieMutableLiveData, page);
    }
}
