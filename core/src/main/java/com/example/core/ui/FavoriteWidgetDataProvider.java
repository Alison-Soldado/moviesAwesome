package com.example.core.ui;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.core.R;
import com.example.core.data.model.movie.Result;
import com.example.core.data.source.local.AppDatabase;
import com.example.core.data.source.local.detail.FavoriteDao;
import com.example.core.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class FavoriteWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Result> results = new ArrayList<>();
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
        executors.diskIO().execute(() -> results = favoriteDao.getAllFavoritesWidget());
    }

    @Override
    public void onDataSetChanged() {
        executors.diskIO().execute(() -> results = favoriteDao.getAllFavoritesWidget());
    }

    @Override
    public void onDestroy() {
        favoriteDao = null;
    }

    @Override
    public int getCount() {
        if (results != null) {
            return results.size();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        String title = results.get(position).getTitle();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget_favorite);
        remoteViews.setTextViewText(R.id.item_widget_favorite_title_app, title);
        return remoteViews;
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
