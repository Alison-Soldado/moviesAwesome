package com.example.detail.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.core.data.model.movie.Result;
import com.example.core.data.source.local.AppDatabase;

public class DetailViewModel extends ViewModel {

    public Long setFavorite(AppDatabase appDatabase, Result result) {
        return appDatabase.favoriteDao().insertFavorite(result);
    }

    public LiveData<Result> getListFavoritesById(AppDatabase appDatabase, long id) {
        return appDatabase.favoriteDao().getFavoriteById(id);
    }

    public void deleteFavorite(AppDatabase appDatabase, Result result) {
        appDatabase.favoriteDao().deleteFavorite(result);
    }
}
