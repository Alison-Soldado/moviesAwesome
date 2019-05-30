package com.example.main.data.source.remote

import com.example.core.BuildConfig
import com.example.core.data.source.remote.CallApi

class ApiUtilsMain {

    val listMovieService: WebServiceMain
        get() = CallApi.getClient(BuildConfig.BASE_URL).create(WebServiceMain::class.java)
}
