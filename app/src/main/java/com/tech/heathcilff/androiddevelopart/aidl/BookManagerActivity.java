package com.tech.heathcilff.androiddevelopart.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tech.heathcilff.androiddevelopart.R;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {
	private static final String TAG = "BookManagerActivity";
	private IOnBookArrivedListener bookArrivedListener = new IOnBookArrivedListener.Stub() {
		@Override
		public void onBookArrived(Book book) throws RemoteException {
			Log.d(TAG, "收到新书啦" + book.toString());
			Log.d(TAG, "线程:" + Thread.currentThread().getName());
		}
	};

	IBookManager iBookManager;
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "连接建立啦");
			iBookManager = IBookManager.Stub.asInterface(service);
			try {
				List<Book> bookList = iBookManager.getBookList();
				Log.d(TAG, bookList.toString());
				iBookManager.addBook(new Book(3, "Python"));
				Log.d(TAG, iBookManager.getBookList().toString());

				iBookManager.registerOnBookArrivedListener(bookArrivedListener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "连接断开啦");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_manager);
		bindService(new Intent(this, BookManagerService.class), connection, BIND_AUTO_CREATE);
		Log.d(TAG, "线程:" + Thread.currentThread().getName());
	}

	@Override
	protected void onDestroy() {
		if(iBookManager != null && bookArrivedListener != null) {
			try {
				iBookManager.unregisterOnBookArrivedListener(bookArrivedListener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		unbindService(connection);
		super.onDestroy();
	}
}
