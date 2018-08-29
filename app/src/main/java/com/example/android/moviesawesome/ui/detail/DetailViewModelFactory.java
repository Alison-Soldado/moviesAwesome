package com.example.android.moviesawesome.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.moviesawesome.data.source.local.AppDatabase;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase appDatabase;
    private final long id;

    DetailViewModelFactory(AppDatabase appDatabase, long id) {
        this.appDatabase = appDatabase;
        this.id = id;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(appDatabase, id);
    }
}