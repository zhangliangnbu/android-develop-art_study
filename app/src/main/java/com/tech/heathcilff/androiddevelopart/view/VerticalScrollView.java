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

public class VerticalScrollView extends RelativeLayout {
	private static final String TAG = "VerticalScrollView";
	public VerticalScrollView(Context context) {
		super(context);
	}

	public VerticalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "dispatchTouchEvent");
//		innerIntercepMethod(ev);
		Log.d(TAG, "evXY:" + ev.getX() + "/" + ev.getY());
		Log.d(TAG, "evRawXY:" + ev.getRawX() + "/" + ev.getRawY());
		Log.d(TAG, "XY:" + getX() + "/" + getY());
		Log.d(TAG, "ScrollXY:" + getScrollX() + "/" + getScrollY());
		return super.dispatchTouchEvent(ev);
	}

	private void innerIntercepMethod(MotionEvent ev) {
		float x = ev.getX();
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				getParent().requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_MOVE:
				float dx = x - lastX;
				float dy = y - lastY;
				boolean enableHorizontalScroll = Math.abs(dx) > Math.abs(dy);
				getParent().requestDisallowInterceptTouchEvent(!enableHorizontalScroll);
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent");
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		printEventAction(event);
		handleVerticalScrollEvent(event);
		return true;
	}

	private float lastX, lastY;
	private void handleVerticalScrollEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				float dy = event.getY() - lastY;
				Log.d(TAG, dy + "-");
				scrollBy(0, (int) -dy);
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		lastX = event.getX();
		lastY = event.getY();
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
