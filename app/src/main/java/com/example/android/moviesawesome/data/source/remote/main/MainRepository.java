package com.example.android.moviesawesome.data.source.remote.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.android.moviesawesome.data.model.Movie;
import com.example.android.moviesawesome.data.source.remote.ApiUtils;
import com.example.android.moviesawesome.data.source.remote.service.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private WebService webService = new ApiUtils().getListRepositoryService();

    public void getMovie(MutableLiveData<Movie> movieMutableLiveData, Integer page) {
        webService.getMoviePopular(page).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                movieMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {

            }
        });
    }
}
