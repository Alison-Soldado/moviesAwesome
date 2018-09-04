package com.example.android.moviesawesome.data.source.remote.main;

import android.support.annotation.NonNull;

import com.example.android.moviesawesome.data.model.Resource;
import com.example.android.moviesawesome.data.model.movie.Movie;
import com.example.android.moviesawesome.data.source.remote.ApiUtils;
import com.example.android.moviesawesome.data.source.remote.WebService;
import com.example.android.moviesawesome.util.SingleLiveEvent;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private WebService webService = new ApiUtils().getListMovieService();

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
