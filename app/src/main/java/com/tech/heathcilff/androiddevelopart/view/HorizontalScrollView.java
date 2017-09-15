package com.tech.heathcilff.androiddevelopart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 *
 * Created by zhangliang on 01/09/2017.
 */

public class HorizontalScrollView extends ViewGroup {
	private static final String TAG = "HorizontalScrollView";
	private Scroller scroller;
	private VelocityTracker velocityTracker;
	public HorizontalScrollView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public HorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		scroller = new Scroller(getContext());
		velocityTracker = VelocityTracker.obtain();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// measure children
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		// if wrap-content
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
		int childCount = getChildCount();

		if(childCount == 0) {
			setMeasuredDimension(0 ,0);
		} else if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode) {
			int width = measureWidth();
			int height = getChildAt(0).getMeasuredHeight();
			Log.d(TAG, width + "/" + height);
			setMeasuredDimension(width, height);
		} else if(MeasureSpec.AT_MOST == widthSpecMode) {
			int width = measureWidth();
			setMeasuredDimension(width, heightSpecSize);
		} else if(MeasureSpec.AT_MOST == heightSpecMode) {
			int height = getChildAt(0).getMeasuredHeight();
			setMeasuredDimension(widthSpecSize, height);
		}
	}

	private int measureWidth() {
		int count  = getChildCount();
		int width = 0;
		for(int i = 0; i < count; i++) {
			width += getChildAt(i).getMeasuredWidth();
		}
		return width;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.d(TAG, l + "/" + t + "/" + r + "/" + b);
		// layout children
		int childCount = getChildCount();
		int left = 0;
		for (int i = 0; i < childCount; i ++) {
			View child = getChildAt(i);
			if(child.getVisibility() != GONE) {
				child.layout(left, 0, left + child.getMeasuredWidth(), child.getMeasuredHeight());
				left += child.getMeasuredWidth();
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "dispatchTouchEvent");
		Log.d(TAG, "evXY:" + ev.getX() + "/" + ev.getY());
		Log.d(TAG, "evRawXY:" + ev.getRawX() + "/" + ev.getRawY());
		Log.d(TAG, "XY:" + getX() + "/" + getY());
		Log.d(TAG, "ScrollXY:" + getScrollX() + "/" + getScrollY());
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent");
		return exInterceptMethod(ev);
	}

	private float lastInterceptX, lastInterceptY;
	private boolean exInterceptMethod(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		boolean intercept = false;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				intercept = false;
				break;
			case MotionEvent.ACTION_MOVE:
				float dx = x - lastInterceptX;
				float dy = y - lastInterceptY;
				intercept = Math.abs(dx) > Math.abs(dy) + 10;
				break;
			case MotionEvent.ACTION_UP:
				intercept = false;
				break;
		}
		lastInterceptX = x;
		lastInterceptY = y;
		// 一旦拦截则不再执行此方法，因此可用lastX/Y存储第一次move事件的位置
		lastX = x;
		lastY = y;

		return intercept;
	}

	@Override
	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
		super.requestDisallowInterceptTouchEvent(disallowIntercept);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		printEventAction(event);
		handleHorizontalScrollEvent(event);
		return true;
	}

	private float lastX, lastY;
	private void handleHorizontalScrollEvent(MotionEvent event) {
		if(velocityTracker == null) {
			velocityTracker = VelocityTracker.obtain();
		}
		velocityTracker.addMovement(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(!scroller.isFinished()) scroller.abortAnimation();
				break;
			case MotionEvent.ACTION_MOVE:
				float dx = event.getX() - lastX;
				Log.d(TAG, dx + "-");
				scrollBy((int)-dx, 0);
				break;
			case MotionEvent.ACTION_UP:
				velocityTracker.computeCurrentVelocity(1000);
				float vx = velocityTracker.getXVelocity();
				Log.d(TAG, "vx:" + vx);
				if(Math.abs(vx) > 50) {
					smoothScrollBy((int)((-vx * 500)/Math.abs(vx)), 0);
				}

				velocityTracker.recycle();
				velocityTracker.clear();
				velocityTracker = null;
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

	// 启动动画，其实改变的是currX和currY
	private void smoothScrollBy(int dx, int dy) {
		scroller.startScroll(getScrollX(), 0, dx, 0, 500);
		invalidate();
	}

	// 执行相关动作 刷新动画
	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
		}
	}
}
