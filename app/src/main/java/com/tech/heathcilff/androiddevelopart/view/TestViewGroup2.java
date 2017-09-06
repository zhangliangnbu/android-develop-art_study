package com.tech.heathcilff.androiddevelopart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 *
 * Created by zhangliang on 01/09/2017.
 */

public class TestViewGroup2 extends RelativeLayout {
	private static final String TAG = "TestViewGroup2";
	public TestViewGroup2(Context context) {
		super(context);
	}

	public TestViewGroup2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TestViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	float lastX,lastY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "dispatchTouchEvent");
		float x = ev.getX();
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				getParent().requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_MOVE:
				float deltaX = x - lastX;
				float deltaY = y - lastY;
				boolean isHorizontalScroll = Math.abs(deltaX) > Math.abs(deltaY);
				getParent().requestDisallowInterceptTouchEvent(isHorizontalScroll);
				break;
			default:
				break;
		}
		lastX = x;
		lastY = y;
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent");
//		getParent().requestDisallowInterceptTouchEvent(true);
//		return super.onInterceptTouchEvent(ev);
		return true;
	}

	@Override
	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
		Log.d(TAG, "requestDisallowInterceptTouchEvent");
		super.requestDisallowInterceptTouchEvent(disallowIntercept);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		printEventAction(event);
//		return super.onTouchEvent(event);
		return true;
	}

	private void printEventAction(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d(TAG, "ACTION_DOWN");
				break;

			case MotionEvent.ACTION_MOVE:
				Log.d(TAG, "ACTION_MOVE");
				break;

			case MotionEvent.ACTION_UP:
				Log.d(TAG, "ACTION_UP");
		}
	}
}
