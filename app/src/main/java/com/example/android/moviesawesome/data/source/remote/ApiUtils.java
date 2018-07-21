package com.example.android.moviesawesome.data.source.remote;


import com.example.android.moviesawesome.data.source.remote.service.WebService;

public class ApiUtils {

    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public WebService getListRepositoryService() {
        return CallApi.getClient(BASE_URL).create(WebService.class);
    }
}
