package com.example.android.moviesawesome.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.moviesawesome.data.model.Resource;
import com.example.android.moviesawesome.data.model.movie.Movie;
import com.example.android.moviesawesome.data.model.movie.Result;
import com.example.android.moviesawesome.data.source.local.AppDatabase;
import com.example.android.moviesawesome.data.source.remote.main.MainRepository;
import com.example.android.moviesawesome.util.SingleLiveEvent;

import java.util.List;

public class MainViewModel extends ViewModel {

    public SingleLiveEvent<Resource<Movie>> movieSingleLiveEvent = new SingleLiveEvent<>();
    private LiveData<List<Result>> resultLiveData;
    private MainRepository mainRepository = new MainRepository();

    void getListMovies(Integer page) {
        mainRepository.getMovie(movieSingleLiveEvent, page);
    }

    void getListMoviesTop(Integer page) {
        mainRepository.getMovieTop(movieSingleLiveEvent, page);
    }

    public LiveData<List<Result>> getListFavorites(AppDatabase appDatabase) {
        return appDatabase.favoriteDao().getAllFavorites();
    }
}
