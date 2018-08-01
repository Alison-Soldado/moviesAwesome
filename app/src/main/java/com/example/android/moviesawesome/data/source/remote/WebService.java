package com.example.android.moviesawesome.data.source.remote;


import com.example.android.moviesawesome.data.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WebService {

    @GET("popular?api_key=PUT HERE")
    Call<Movie> getMoviePopular(@Query("page") Integer page);

    @GET("top_rated?api_key=PUT HERE")
    Call<Movie> getMovieTopRated(@Query("page") Integer page);
}
