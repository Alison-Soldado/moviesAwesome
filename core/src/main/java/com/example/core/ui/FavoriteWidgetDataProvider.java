package com.example.core.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.core.R;
import com.example.core.data.model.movie.Result;
import com.example.core.data.source.local.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoriteWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Intent intent;
    private AppDatabase appDatabase;
    private List<Result> results = new ArrayList<>();

    FavoriteWidgetDataProvider(@NonNull Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        this.appDatabase = AppDatabase.getInstance(context.getApplicationContext());
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view =
                new RemoteViews(context.getPackageName(), R.layout.item_widget_favorite);
        view.setTextViewText(R.id.item_widget_favorite_title, results.get(position).getTitle());
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

    private void initData() {
        results = (List<Result>) appDatabase.favoriteDao().getAllFavorites();
    }
}
