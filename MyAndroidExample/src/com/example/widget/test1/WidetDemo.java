package com.example.widget.test1;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.R;

public class WidetDemo extends AppWidgetProvider {
	/** Called when the activity is first created. */

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1,
				1000);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	private class MyTime extends TimerTask {
		RemoteViews remoteViews;
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;

		public MyTime(Context context, AppWidgetManager appWidgetManager) {
			this.appWidgetManager = appWidgetManager;
			remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widgetmain1);

			thisWidget = new ComponentName(context, WidetDemo.class);
		}

		public void run() {

			Calendar c = new GregorianCalendar(2012, 12, 2);
			long mm = (c.getTimeInMillis() - System.currentTimeMillis()) / 1000;

			remoteViews.setTextViewText(R.id.wordcup, "倒计时还有" + mm + "秒");

			appWidgetManager.updateAppWidget(thisWidget, remoteViews);

		}

	}

}