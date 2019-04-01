package com.example.moviesawesome.free.free;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.R;

import java.util.Objects;


/**
 * Implementation of App Widget functionality.
 */
public class FavoriteWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews remoteViews = setRemoteAdapter(context);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (Objects.requireNonNull(intent.getAction()).equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisWidget = new ComponentName(context.getApplicationContext(), FavoriteWidget.class);

                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                RemoteViews remoteViews = setRemoteAdapter(context);

                appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_favorite_list);
                onUpdate(context, appWidgetManager, appWidgetIds);
            }
        }
    }

    private static RemoteViews setRemoteAdapter(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_favorite);
        remoteViews.setRemoteAdapter(R.id.widget_favorite_list, new Intent(context, FavoriteWidgetService.class));
        remoteViews.setEmptyView(R.id.widget_favorite_list, R.id.widget_favorite_empty);
        return remoteViews;
    }
}

