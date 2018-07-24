package com.example.android.moviesawesome.data.source.remote;


import com.example.android.moviesawesome.data.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WebService {

    @GET("popular?api_key=792eb02e9767b4c71aa0bfe6d7c51e60")
    Call<Movie> getMoviePopular(@Query("page") Integer page);
}
