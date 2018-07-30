package com.example.android.moviesawesome.data.source.remote;


public class ApiUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public WebService getListMovieService() {
        return CallApi.getClient(BASE_URL).create(WebService.class);
    }
}
