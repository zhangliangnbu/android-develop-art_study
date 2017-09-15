package com.tech.heathcilff.androiddevelopart.remoteViews;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.tech.heathcilff.androiddevelopart.R;
import com.tech.heathcilff.androiddevelopart.view.threeTouchEvent.ThreeTouchEventExActivity;

public class NotificationDemoActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_demo);

		findViewById(R.id.btn_send_notification).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//		createNotification();
				createNotificationByRemoteViews();
			}
		});
	}

	private void createNotification() {
		Intent intent = new Intent(this, ThreeTouchEventExActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		Notification notification = new Notification.Builder(this)
				.setContentTitle("ha ha title")
				.setContentText("ha ha content text")
				.setSmallIcon(R.mipmap.ic_launcher_round)
				.setAutoCancel(true)
				.setContentIntent(pendingIntent)
				.build();
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(1, notification);
	}

	private void createNotificationByRemoteViews() {
		Intent intent = new Intent(this, ThreeTouchEventExActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
		remoteViews.setTextViewText(R.id.tv_title, "ha ha title");
		remoteViews.setTextViewText(R.id.tv_content, "ha ha content ... he he \n ha ha \n ha ha \n ha ha \n ha ha \n ha ha \n ha ha \n ha ha ");
		remoteViews.setImageViewResource(R.id.iv_icon, R.mipmap.ic_launcher);

		Notification notification = new Notification.Builder(this)
//				.setContentTitle("ha ha title")
//				.setContentText("ha ha content text")
				.setSmallIcon(R.mipmap.ic_launcher_foreground)
				.setContent(remoteViews)
				.setAutoCancel(true)
				.setContentIntent(pendingIntent)
				.build();
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(1, notification);
	}
}
