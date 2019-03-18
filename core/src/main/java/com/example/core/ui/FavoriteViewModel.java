package com.example.core.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.core.data.model.movie.Result;
import com.example.core.data.source.local.AppDatabase;

import java.util.List;

public class FavoriteViewModel extends ViewModel {
    public LiveData<List<Result>> getListFavorites(AppDatabase appDatabase) {
        return appDatabase.favoriteDao().getAllFavorites();
    }
}
