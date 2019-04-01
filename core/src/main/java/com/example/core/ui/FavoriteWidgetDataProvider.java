package com.example.core.ui;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.core.R;
import com.example.core.data.model.movie.Result;
import com.example.core.data.source.local.AppDatabase;
import com.example.core.data.source.local.detail.FavoriteDao;
import com.example.core.util.AppExecutors;

import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class FavoriteWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private LiveData<List<Result>> results;
    private AppExecutors executors = new AppExecutors();
    private FavoriteDao favoriteDao;

    FavoriteWidgetDataProvider(@NonNull Context context) {
        this.context = context;
        favoriteDao = Room.databaseBuilder(context, AppDatabase.class, "favoriteDatabase")
                .build()
                .favoriteDao();
    }

    @Override
    public void onCreate() {
        executors.diskIO().execute(() -> results = favoriteDao.getAllFavorites());
    }

    @Override
    public void onDataSetChanged() {
        executors.diskIO().execute(() -> results = favoriteDao.getAllFavorites());
    }

    @Override
    public void onDestroy() {
        favoriteDao = null;
    }

    @Override
    public int getCount() {
        if (results.getValue() != null) {
            return results.getValue().size();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view =
                new RemoteViews(context.getPackageName(), R.layout.item_widget_favorite);
        if (results.getValue() != null) {
            view.setTextViewText(R.id.item_widget_favorite_title, results.getValue().get(position).getTitle());
        } else {
            view.setTextViewText(R.id.item_widget_favorite_title, "Nothing");
        }
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
