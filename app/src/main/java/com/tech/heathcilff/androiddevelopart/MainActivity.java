package com.tech.heathcilff.androiddevelopart;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tech.heathcilff.androiddevelopart.socket.TCPClientActivity;
import com.tech.heathcilff.androiddevelopart.view.sameDirectionTouch.SameDirectionTouchActivity2;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_main);
		UserManager.sUserId ++;
		Log.d(TAG, UserManager.sUserId + "");

		View view = findViewById(R.id.tv_toSecond);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(), SecondActivity.class));
			}
		});

		findViewById(R.id.btn_toSecond).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("androiddevelopart.action.second");
				intent.putExtra(Intent.EXTRA_TEXT, "hello world");
				toSMS();
			}
		});

		findViewById(R.id.btn_toFifth).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = Intents.toFifthActivity(getApplicationContext());
				intent.putExtra("string", "ABC");
				Bundle bundle = new Bundle();
				bundle.putString("bundle", "bundle ha ha");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn_toSixth).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toSixthhActivity(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toServer).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toBookManagerActivity(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toMessengerActivity).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toMessengerActivity(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toBookProviderPage).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toBookProviderPage(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toTCPClientPage).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), TCPClientActivity.class));
			}
		});

		findViewById(R.id.btn_toViewPage).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), SameDirectionTouchActivity2.class));
			}
		});
	}

	private void toSMS() {
		Intent intent = new Intent(Intent.ACTION_SENDTO);// action
		intent.setData(Uri.parse("smsto:10086"));// data
		intent.putExtra("sms_body", "手头有点紧，借点钱吧~~");
		if(intent.resolveActivity(getPackageManager()) != null){
			startActivity(intent);
		}
	}

	private void startActivityByShowChooser() {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setDataAndType(Uri.parse("content://bac"), "text/plain");
		// Always use string resources for UI text.
		// This says something like "Share this photo with"
		String title = "Share this with ...";
		// Create intent to show the chooser dialog
		Intent chooser = Intent.createChooser(sendIntent, title);

		// Verify the original intent will resolve to at least one activity
		if (sendIntent.resolveActivity(getPackageManager()) != null) {
			startActivity(chooser);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(TAG, "onRestoreInstanceState");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged");
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