package com.tech.heathcilff.androiddevelopart.view.threeTouchEvent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.tech.heathcilff.androiddevelopart.view.ViewUtil;

/**
 *
 * Created by zhangliang on 06/09/2017.
 */

public class InnerHorizontalViewGroup extends FrameLayout {
	private static final String TAG = "inner";
	private View child;
	public InnerHorizontalViewGroup(@NonNull Context context) {
		super(context);
	}

	public InnerHorizontalViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public InnerHorizontalViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		child = getChildAt(0);
	}

//	private boolean interceptByParent = false;
	private float lastDispatchX, lastDispatchY;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		float x = ev.getX(), y = ev.getY();
		Log.d(ViewUtil.getTouchTag(TAG, ev), "dispatchTouchEvent-xy:" + ev.getX() + "/" + ev.getY());
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				getParent().requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_MOVE:
				float dx = x - lastDispatchX;
				float dy = y - lastDispatchY;
				Log.d(TAG, "dXY:" + dx + "/" + dy);
//				interceptByParent = !(Math.abs(dx) > Math.abs(dy));
				getParent().requestDisallowInterceptTouchEvent(Math.abs(dx) > Math.abs(dy));
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		lastDispatchX = x;
		lastDispatchY = y;
		return super.dispatchTouchEvent(ev);
	}

	private float lastX;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(ViewUtil.getTouchTag(TAG, event), "onTouchEvent-xy:" + event.getX() + "/" + event.getY());
		float x = event.getX();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
//				Log.d(TAG, interceptByParent + "");
//				if(interceptByParent) return false;
				float dx = x - lastX;
				boolean isAtLeftAndMoveToLeft = getScrollX() >= child.getLeft() && dx < 0;
				boolean isAtRightAndMoveToRight = -getScrollX() >= getWidth() - child.getLeft() - child.getWidth() && dx > 0;
				if(isAtLeftAndMoveToLeft) {
					scrollTo(child.getLeft(), 0);
				} else if(isAtRightAndMoveToRight){
					scrollTo(-(getWidth() - child.getLeft() - child.getWidth()), 0);
				} else {
					scrollBy((int) -dx, 0);
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		lastX = x;
		return true;
	}
}
