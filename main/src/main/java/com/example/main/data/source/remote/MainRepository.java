package com.example.main.data.source.remote;

import android.support.annotation.NonNull;


import com.example.core.data.model.Resource;
import com.example.core.data.model.movie.Movie;
import com.example.core.util.SingleLiveEvent;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private WebServiceMain webService = new ApiUtilsMain().getListMovieService();

    public void getMovie(SingleLiveEvent<Resource<Movie>> movieSingleLiveEvent, Integer page) {
        webService.getMoviePopular(page).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                movieSingleLiveEvent.setValue(Resource.success(Objects.requireNonNull(response.body())));
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                movieSingleLiveEvent.setValue(Resource.error(t.getMessage(), null));
            }
        });
    }

    public void getMovieTop(SingleLiveEvent<Resource<Movie>> movieTopSingleLiveEvent, Integer page) {
        webService.getMovieTopRated(page).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                movieTopSingleLiveEvent.setValue(Resource.success(Objects.requireNonNull(response.body())));
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                movieTopSingleLiveEvent.setValue(Resource.error(t.getMessage(), null));
            }
        });
    }
}
