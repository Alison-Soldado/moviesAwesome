package com.example.main.data.source.remote;


import com.example.core.BuildConfig;
import com.example.core.data.model.movie.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WebServiceMain {

    String API_KEY = BuildConfig.API_KEY_MOVIE_DB;

    @GET("popular?api_key=" + API_KEY)
    Call<Movie> getMoviePopular(@Query("page") Integer page);

    @GET("top_rated?api_key=" + API_KEY)
    Call<Movie> getMovieTopRated(@Query("page") Integer page);
}
