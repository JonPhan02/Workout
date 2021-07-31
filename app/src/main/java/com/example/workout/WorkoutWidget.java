package com.example.workout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Implementation of App Widget functionality.
 */
class Count {
    public static Integer count = 0;
}

public class WorkoutWidget extends AppWidgetProvider {

    private static String add = "addButton";
    private static String sub = "minusButton";
    private static String clear = "clearButton";
    private static String start = "startButton";
    private static String stop = "stopButton";
    private static String reset = "resetButton";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.workout_widget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        String temp = "";
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.workout_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName watchWidget = new ComponentName(context, WorkoutWidget.class);
        if (add.equals(action)){
            Count.count++;
            temp = Count.count.toString();
            remoteViews.setTextViewText(R.id.setCounter, temp);
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        } else if (sub.equals(action) && Count.count > 0){
            Count.count--;
            temp = Count.count.toString();
            remoteViews.setTextViewText(R.id.setCounter, temp);
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        } else if (clear.equals(action)){
            Count.count = 0;
            temp = Count.count.toString();
            remoteViews.setTextViewText(R.id.setCounter, temp);
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        } else if (start.equals(action)){
            remoteViews.setChronometer(R.id.timer, SystemClock.elapsedRealtime(), null, true);
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        } else if (reset.equals(action)){
            remoteViews.setChronometer(R.id.timer, SystemClock.elapsedRealtime(), null, false);
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        }
    };

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.workout_widget);
            ComponentName watchWidget = new ComponentName(context, WorkoutWidget.class);
            remoteViews.setOnClickPendingIntent(R.id.addButton, getPendingSelfIntent(context, add));
            remoteViews.setOnClickPendingIntent(R.id.minusButton, getPendingSelfIntent(context, sub));
            remoteViews.setOnClickPendingIntent(R.id.clearButton, getPendingSelfIntent(context, clear));
            remoteViews.setOnClickPendingIntent(R.id.startButton, getPendingSelfIntent(context, start));
            remoteViews.setOnClickPendingIntent(R.id.resetButton, getPendingSelfIntent(context, reset));
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}