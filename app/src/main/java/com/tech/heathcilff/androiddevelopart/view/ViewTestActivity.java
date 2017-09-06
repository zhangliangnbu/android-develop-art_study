package com.tech.heathcilff.androiddevelopart.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.tech.heathcilff.androiddevelopart.R;

public class ViewTestActivity extends AppCompatActivity {

	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_view_test);// test view
		setContentView(R.layout.activity_view_test_horizontal);// horizontal view event conflict


		final HorizontalScrollView parent = (HorizontalScrollView) findViewById(R.id.hsv);
//		findViewById(R.id.btn_translate).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				parent.translate();
//			}
//		});
//		findViewById(R.id.btn_recovery).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				parent.recovery();
//			}
//		});

		view = findViewById(R.id.tv_test);

	}

	@Override
	protected void onStart() {
		super.onStart();
		view.post(new Runnable() {
			@Override
			public void run() {
				printViewSize("onStart-post");
			}
		});

		ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
		viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				printViewSize("onGlobalLayout");
			}
		});


	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus) {
			printViewSize("onWindowFocusChanged");
		}
	}

	private void printViewSize(String tag) {
		int width = view.getMeasuredWidth();
		int height = view.getMeasuredHeight();
		Log.d(tag, width + "/" + height);
	}
}
