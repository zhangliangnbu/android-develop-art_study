package com.tech.heathcilff.androiddevelopart.remoteViews;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.tech.heathcilff.androiddevelopart.R;

/**
 *
 * Created by zhangliang on 14/09/2017.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {

	public static final String TAG = "MyAppWidgetProvider";
	public static final String CLICK_ACTION = "com.tech.heathcilff.androiddevelopart.action.CLICK";
	public static int clickCount = 0;
	public MyAppWidgetProvider() {
		super();
	}

	// 事件如点击等可放在这里处理
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		Log.d(TAG, "onReceive-action:" + intent.getAction());
		if(CLICK_ACTION.equals(intent.getAction())) {
			Toast.makeText(context, "click it", Toast.LENGTH_LONG).show();
			// .....
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
			remoteViews.setTextViewText(R.id.tv_widget, (clickCount ++) + "");
			Log.d(TAG, "count:" + clickCount);
			AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
			widgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteViews);
		}
	}

	// updatePeriodMils控制的自动更新在这里处理
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d(TAG, "onUpdate-ids len:" + appWidgetIds.length);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		Intent intent = new Intent();
		intent.setAction(CLICK_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.iv_widget, pendingIntent);// 当点击的时候 会发出CLICK_ACTION事件广播
		appWidgetManager.updateAppWidget(appWidgetIds[0], remoteViews);
	}
}
