package com.example.android.moviesawesome.data.source.remote;


import com.example.android.moviesawesome.BuildConfig;

public class ApiUtils {

    public WebService getListMovieService() {
        return CallApi.getClient(BuildConfig.BASE_URL).create(WebService.class);
    }
}
