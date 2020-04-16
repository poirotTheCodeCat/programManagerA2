package com.example.programmanagera2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

import static com.example.programmanagera2.Project.getAllProjects;
import static com.example.programmanagera2.Project.getProjectCount;

/**
 * Implementation of App Widget functionality.
 */
public class CreateNewProjectWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        int projectCount = Project.getProjectCount(context);

        String responseText;
        if (projectCount > 0) {
            responseText = context.getString(R.string.got_projects);
        } else {
            responseText = context.getString(R.string.got_no_projects);
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.create_new_project_widget);

        views.setTextViewText(R.id.button_for_text2, responseText);
        views.setTextViewText(R.id.appwidget_count, String.valueOf(projectCount));

        Intent intentSync = new Intent(context, AppWidgetProvider.class);
        intentSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingSync = PendingIntent.getBroadcast(context,0,
                intentSync, PendingIntent.FLAG_UPDATE_CURRENT);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

