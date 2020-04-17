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
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            //create intent for receiving update from app
            Intent intentSync = new Intent(context, AppWidgetProvider.class);
            intentSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            PendingIntent pendingSync = PendingIntent.getBroadcast(context,0,
                    intentSync, PendingIntent.FLAG_UPDATE_CURRENT);

            //create listeners for both buttons
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // number of project number
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.create_new_project_widget);
            views.setOnClickPendingIntent(R.id.appwidget_count, pendingIntent);

            //add new button
            Intent intent2 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context,
                    0, intent, 0);

            views.setOnClickPendingIntent(R.id.appwidget_button, pendingIntent2);

            int projectCount = Project.getProjectCount(context);

            String responseText;
            if (projectCount > 0) {
                responseText = context.getString(R.string.got_projects);
            } else {
                responseText = context.getString(R.string.got_no_projects);
            }

            views.setTextViewText(R.id.button_for_text2, responseText);
            views.setTextViewText(R.id.appwidget_count, String.valueOf(projectCount));

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
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

