package com.example.wyyu.gitsamlpe.test.function.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.MainActivity;

public class FloatWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		for (int id : appWidgetIds) {
			Intent intent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_float_widget);
			remoteViews.setOnClickPendingIntent(R.id.cover, pendingIntent);
			appWidgetManager.updateAppWidget(id, remoteViews);
		}
	}

	@Override
	public void onEnabled(Context context) {

	}

	@Override
	public void onDisabled(Context context) {

	}
}
