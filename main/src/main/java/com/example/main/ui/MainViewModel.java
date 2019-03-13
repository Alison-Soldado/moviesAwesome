package com.example.main.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.core.data.model.Resource;
import com.example.core.data.model.movie.Movie;
import com.example.core.data.model.movie.Result;
import com.example.core.data.source.local.AppDatabase;
import com.example.main.data.source.remote.MainRepository;
import com.example.main.data.source.remote.SingleLiveEvent;

import java.util.List;

public class MainViewModel extends ViewModel {

    SingleLiveEvent<Resource<Movie>> movieSingleLiveEvent = new SingleLiveEvent<>();
    private MainRepository mainRepository = new MainRepository();

    void getListMovies(Integer page) {
        mainRepository.getMovie(movieSingleLiveEvent, page);
    }

    void getListMoviesTop(Integer page) {
        mainRepository.getMovieTop(movieSingleLiveEvent, page);
    }

    LiveData<List<Result>> getListFavorites(AppDatabase appDatabase) {
        return appDatabase.favoriteDao().getAllFavorites();
    }
}
