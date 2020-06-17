package com.tech.heathcilff.androiddevelopart;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class FourthActivity extends AppCompatActivity {
	private static final String TAG = "FourthActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_fourth);

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
	}
}
