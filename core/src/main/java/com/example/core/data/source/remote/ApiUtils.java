package com.example.core.data.source.remote;

import com.example.core.BuildConfig;

public class ApiUtils {

    public WebService getList() {
        return CallApi.getClient(BuildConfig.BASE_URL).create(WebService.class);
    }
}
