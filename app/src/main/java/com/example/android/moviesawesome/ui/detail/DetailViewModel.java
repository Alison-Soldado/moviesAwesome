package com.example.android.moviesawesome.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.moviesawesome.data.model.movie.Result;
import com.example.android.moviesawesome.data.source.local.AppDatabase;

public class DetailViewModel extends ViewModel {

    private LiveData<Result> result;
    private AppDatabase appDatabase;

    DetailViewModel(AppDatabase appDatabase, long id) {
        this.appDatabase = appDatabase;
        this.result = appDatabase.detailDao().loadFavoriteById(id);
    }

    public LiveData<Result> getFavorite() {
        return result;
    }

    public void insertFavorite(Result result) {
        if (this.result.getValue() != null) {
            if (this.result.getValue().getId() == result.getId()) {
                appDatabase.detailDao().insertFavorite(result);
            } else {
                appDatabase.detailDao().deleteFavorite(result);
            }
        }
    }
}
