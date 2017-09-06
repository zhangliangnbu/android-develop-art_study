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

public class TestViewGroup extends RelativeLayout {
	private static final String TAG = "TestViewGroup";
	public TestViewGroup(Context context) {
		super(context);
	}

	public TestViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent");
		return defaultInterceptEvent(ev);
//		return super.onInterceptTouchEvent(ev);
//		return true;
	}

	private boolean defaultInterceptEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
//			case MotionEvent.ACTION_UP:
				return false;
			default:
				return true;
		}
	}

	@Override
	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//		Log.d(TAG, "requestDisallowInterceptTouchEvent");
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

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
