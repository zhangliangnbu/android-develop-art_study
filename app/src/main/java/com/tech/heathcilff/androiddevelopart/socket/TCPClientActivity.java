package com.tech.heathcilff.androiddevelopart.socket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.tech.heathcilff.androiddevelopart.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClientActivity extends AppCompatActivity {
	private static final String TAG = "TCPClientActivity";
	private PrintWriter writer;
	private Socket socket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tcpclient);
		startService(new Intent(this, TCPServerService.class));
		new Thread(new TCPClientWorker()).start();


		final EditText et = (EditText) findViewById(R.id.et_msg);
		findViewById(R.id.btn_send_msg).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(writer == null) {
					Log.d(TAG, "客户端关闭或异常啦");
					return;
				}
				if(TextUtils.isEmpty(et.getText())) {
					Log.d(TAG, "请输入消息");
					return;
				}

				new Thread(new Runnable() {
					@Override
					public void run() {
						writer.println(et.getText().toString());
						Log.d(TAG, "客户端发消息啦:" + et.getText().toString());
					}
				}).start();

			}
		});

	}

	@Override
	protected void onDestroy() {
		if(socket != null) {
			try {
				socket.shutdownInput();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.onDestroy();
	}

	private class TCPClientWorker implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(5000);// 等待服务器建立
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			socket = null;
			while (socket == null) {
				try {
					socket = new Socket("localhost", 8688);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				while (!TCPClientActivity.this.isFinishing() && !socket.isClosed()) {
					String message = reader.readLine();
					Log.d(TAG, "客户端收到消息啦:" + message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
					if(writer != null) {
						writer.flush();
						writer.close();
					}
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
