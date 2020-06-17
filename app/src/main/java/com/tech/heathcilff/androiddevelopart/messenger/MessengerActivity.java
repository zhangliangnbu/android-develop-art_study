package com.tech.heathcilff.androiddevelopart.messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;

import com.tech.heathcilff.androiddevelopart.R;

public class MessengerActivity extends AppCompatActivity {

	public static final int MSG_FROM_CLIENT = 10;
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Messenger messenger = new Messenger(service);
			Message message = Message.obtain(null, MSG_FROM_CLIENT);
			Bundle bundle = new Bundle();
			bundle.putString("msg", "hello, there is client");
			message.setData(bundle);
			try {
				messenger.send(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messenger);
		Intent intent = new Intent(this, MessengerService.class);
		bindService(intent, connection, BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		unbindService(connection);
		super.onDestroy();
	}
}
