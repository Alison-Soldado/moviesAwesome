package com.example.core.util;

import android.content.Intent;

import com.example.core.data.model.movie.Result;

public final class Router {

    public static Intent provideToDetailIntent(Result result) {
        return new Intent("com.example.moviesawesome.CUSTOM_ACTION")
                .putExtra("result", result);
    }
}
