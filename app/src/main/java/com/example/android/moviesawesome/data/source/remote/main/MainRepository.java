package com.example.android.moviesawesome.data.source.remote.main;

import android.support.annotation.NonNull;

import com.example.android.moviesawesome.data.model.Movie;
import com.example.android.moviesawesome.data.source.remote.ApiUtils;
import com.example.android.moviesawesome.data.source.remote.WebService;
import com.example.android.moviesawesome.util.SingleLiveEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private WebService webService = new ApiUtils().getListMovieService();

    public void getMovie(SingleLiveEvent<Movie> movieSingleLiveEvent, Integer page) {
        webService.getMoviePopular(page).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                movieSingleLiveEvent.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                // TODO: Desenvolver caso de erro na lista de filmes
            }
        });
    }

    public void getMovieTop(SingleLiveEvent<Movie> movieTopSingleLiveEvent, Integer page) {
            webService.getMovieTopRated(page).enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                    movieTopSingleLiveEvent.setValue(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                    // TODO: Desenvolver caso de erro na lista de filmes rated top
                }
            });
    }
}
