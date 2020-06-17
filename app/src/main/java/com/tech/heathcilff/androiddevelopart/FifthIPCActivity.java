package com.tech.heathcilff.androiddevelopart;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FifthIPCActivity extends AppCompatActivity {
	private static final String TAG = "FifthIPCActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fifth_ipc);

		UserManager.sUserId ++;
		Log.d(TAG, UserManager.sUserId + "");

		Log.d(TAG, getIntent().getStringExtra("string"));
		Log.d(TAG, getIntent().getExtras().getString("bundle"));
	}
}
