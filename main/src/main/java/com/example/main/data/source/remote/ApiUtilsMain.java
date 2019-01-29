package com.example.main.data.source.remote;

import com.example.core.BuildConfig;
import com.example.core.data.source.remote.CallApi;

public class ApiUtilsMain {

    public WebServiceMain getListMovieService() {
        return CallApi.getClient(BuildConfig.BASE_URL).create(WebServiceMain.class);
    }
}
