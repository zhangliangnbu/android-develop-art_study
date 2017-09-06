package com.tech.heathcilff.androiddevelopart.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class MessengerService extends Service {
	private static final String TAG = "MessengerService";

	private static class MessengerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MessengerActivity.MSG_FROM_CLIENT:
					Log.d(TAG, msg.getData().getString("msg"));
					break;
				default:
					super.handleMessage(msg);
			}
		}
	}
	private final Messenger messenger = new Messenger(new MessengerHandler());

	public MessengerService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return messenger.getBinder();
	}
}
