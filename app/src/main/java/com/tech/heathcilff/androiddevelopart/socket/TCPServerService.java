package com.tech.heathcilff.androiddevelopart.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPServerService extends Service {
	private static final String TAG = "TCPServerService";

	private AtomicBoolean isServiceDestroyed = new AtomicBoolean(false);

	public TCPServerService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(new TCPWorker()).start();
	}

	@Override
	public void onDestroy() {
		isServiceDestroyed.compareAndSet(true, false);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private class TCPWorker implements Runnable {

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(8688);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(serverSocket == null) {
				throw new IllegalStateException("server socket is null");
			}
			while (!isServiceDestroyed.get()) {
				try {
					Socket client = serverSocket.accept();
					Log.d(TAG, "建立了一个连接啦");
					responseClient(client);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void responseClient(final Socket socket) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				BufferedReader reader = null;
//				PrintWriter writer = null;
				BufferedWriter writer = null;
				try {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//					writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ,true);
					writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}

				int messageCount = 0;
				while (!isServiceDestroyed.get()) {
					try {
						String message = reader.readLine();
						Log.d(TAG, "服务端收到消息啦:" + message);
						if(message == null) {
							Log.d(TAG, "连接关闭啦");
							break;
						}

						Thread.sleep(1000);
//						writer.println("服务端发消息啦:" + (messageCount ++));
						writer.write("服务端发消息啦:" + (messageCount ++));
						writer.newLine();
						writer.flush();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				try {
					reader.close();
					writer.flush();
					writer.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
