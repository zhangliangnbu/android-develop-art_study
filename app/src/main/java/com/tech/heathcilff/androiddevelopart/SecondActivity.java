package com.tech.heathcilff.androiddevelopart;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
	private static final String TAG = "SecondActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_second);

		findViewById(R.id.btn_toSecond).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toSecondActivity(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toThird).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toThirdActivity(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toFourth).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toFourthActivity(getApplicationContext()));
			}
		});

		printIntentInfo();
	}

	private void printIntentInfo() {
		Log.d(TAG, getIntent().getAction());
		if(getIntent().getCategories() != null) {
			Log.d(TAG, getIntent().getCategories().toString());
		}
		Log.d(TAG, getIntent().getStringExtra(Intent.EXTRA_TEXT));
		if(getIntent().getData() != null) {
			Log.d(TAG, getIntent().getData().getAuthority() + "-"
					+ getIntent().getDataString() + "-"
					+ getIntent().getData().getPath());
			printFileInfo();
			printFileContent();
		}
	}

	private void printFileContent(){
		Uri uri = getIntent().getData();
		try {
			ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
			if (parcelFileDescriptor != null) {
				FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
				String content = getFileContent(fileDescriptor);
				Log.d(TAG, content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFileContent(FileDescriptor fileDescriptor){
		FileInputStream is = null;
		try{
			is = new FileInputStream(fileDescriptor);
			byte[] buff = new byte[1024];
			int len = 0;
			StringBuilder sb = new StringBuilder();
			while ((len = is.read(buff, 0, buff.length)) != -1) {
				sb.append(new String(buff, 0, len));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void printFileInfo() {

		Uri returnUri = getIntent().getData();
		if (returnUri == null) return;
		Cursor returnCursor =
				getContentResolver().query(returnUri, null, null, null, null);
		int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
		int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
		returnCursor.moveToFirst();
		Log.d(TAG, returnCursor.getString(nameIndex));
		Log.d(TAG, Long.toString(returnCursor.getLong(sizeIndex)));
		returnCursor.close();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
}
