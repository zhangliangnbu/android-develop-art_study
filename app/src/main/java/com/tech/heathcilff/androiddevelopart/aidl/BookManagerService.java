package com.tech.heathcilff.androiddevelopart.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

	private static final String TAG = "BMS";
	private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
	private RemoteCallbackList<IOnBookArrivedListener> onBookArrivedListeners = new RemoteCallbackList<>();// CS对象一致性
	private AtomicBoolean isServiceAlive = new AtomicBoolean(false);// 线程安全
	private Binder binder = new IBookManager.Stub() {
		@Override
		public List<Book> getBookList() throws RemoteException {
			return bookList;
		}

		@Override
		public void addBook(Book book) throws RemoteException {
			bookList.add(book);
		}

		@Override
		public void registerOnBookArrivedListener(IOnBookArrivedListener onBookArrivedListener) throws RemoteException {
			onBookArrivedListeners.register(onBookArrivedListener);
			Log.d(TAG, "注册啦_当前数目为:" + getRemoteCallbackCount());
		}

		@Override
		public void unregisterOnBookArrivedListener(IOnBookArrivedListener onBookArrivedListener) throws RemoteException {
			onBookArrivedListeners.unregister(onBookArrivedListener);
			Log.d(TAG, "注销啦_当前数目为:" + getRemoteCallbackCount());
		}
	};

	public BookManagerService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		bookList.add(new Book(1, "Android"));
		bookList.add(new Book(2, "IOS"));
		isServiceAlive.compareAndSet(false, true);
		new Thread(new NewBookThread()).start();
	}

	@Override
	public void onDestroy() {
		isServiceAlive.compareAndSet(true, false);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	private int getRemoteCallbackCount() {
		int count = onBookArrivedListeners.beginBroadcast();
		onBookArrivedListeners.finishBroadcast();
		return count;
	}

	private class NewBookThread implements Runnable {

		@Override
		public void run() {
			while (isServiceAlive.get()) {
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Book book = new Book(bookList.size(), "new_book_" + bookList.size());
				bookList.add(book);
				int count = onBookArrivedListeners.beginBroadcast();
				Log.d(TAG, "发新书啦!!!");
				for (int i = 0; i < count; i ++) {
					IOnBookArrivedListener listener = onBookArrivedListeners.getBroadcastItem(i);
					try {
						listener.onBookArrived(book);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				onBookArrivedListeners.finishBroadcast();
			}
		}
	}
}
