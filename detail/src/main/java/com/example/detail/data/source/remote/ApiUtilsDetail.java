package com.example.detail.data.source.remote;

import com.example.core.BuildConfig;
import com.example.core.data.source.remote.CallApi;

public class ApiUtilsDetail {

    public WebServiceDetail getList() {
        return CallApi.getClient(BuildConfig.BASE_URL).create(WebServiceDetail.class);
    }
}
