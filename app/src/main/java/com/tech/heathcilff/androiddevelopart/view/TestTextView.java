package com.tech.heathcilff.androiddevelopart.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;

/**
 *
 * Created by zhangliang on 31/08/2017.
 */

public class TestTextView extends AppCompatTextView {
	private static final String TAG = "TestTextView";
	private GestureDetector gestureDetector;

	public TestTextView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public TestTextView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public TestTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				Log.d(TAG, "onDown");
				return true;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				Log.d(TAG, "onShowPress");
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				Log.d(TAG, "onSingleTapUp");
				return true;
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				Log.d(TAG, "onScroll:" + distanceX + "/" + distanceY);
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				Log.d(TAG, "onLongPress");
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				Log.d(TAG, "onFling:" + velocityX + "/" + velocityY);
				return true;
			}
		});

		gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				Log.d(TAG, "onSingleTapConfirmed");
				return true;
			}

			@Override
			public boolean onDoubleTap(MotionEvent e) {
				Log.d(TAG, "onDoubleTap");
				return true;
			}

			@Override
			public boolean onDoubleTapEvent(MotionEvent e) {
				Log.d(TAG, "onDoubleTapEvent");
				return true;
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.d(TAG, "dispatchTouchEvent");
		return super.dispatchTouchEvent(event);
	}


	private VelocityTracker velocityTracker;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		printEventAction(event);
		return true;
//		return testGestureDetector(event);
//		return testVelocity(event);
	}

	private boolean testGestureDetector(MotionEvent event) {
		if(gestureDetector != null) {
			return gestureDetector.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

	private boolean testOnlyConsumeDown(MotionEvent event) {
		if(gestureDetector != null) {
			gestureDetector.onTouchEvent(event);
		}
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			return true;
		} else {
			return false;
		}
	}


	private boolean testVelocity(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d(TAG, "ACTION_DOWN");
				if(velocityTracker == null) {
					velocityTracker = VelocityTracker.obtain();
				} else {
					velocityTracker.clear();
				}
				velocityTracker.addMovement(event);
				break;

			case MotionEvent.ACTION_MOVE:
				Log.d(TAG, "ACTION_MOVE");
				if(velocityTracker != null) {
					velocityTracker.addMovement(event);
					velocityTracker.computeCurrentVelocity(1000);
					float xVelocity = velocityTracker.getXVelocity();
					float yVelocity = velocityTracker.getYVelocity();
					Log.d(TAG, xVelocity + "-" + yVelocity);
				}
				break;

			case MotionEvent.ACTION_UP:
				Log.d(TAG, "ACTION_UP");
				if(velocityTracker != null) {
					velocityTracker.clear();
					velocityTracker.recycle();
					velocityTracker = null;
				}
		}

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
