package com.tech.heathcilff.androiddevelopart.view.sameDirectionTouch;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 *
 * Created by zhangliang on 06/09/2017.
 */

public class InnerVerticalScrollViewGroup2 extends FrameLayout {
	private static final String TAG = "inner";
	private View child;
	public InnerVerticalScrollViewGroup2(@NonNull Context context) {
		super(context);
	}

	public InnerVerticalScrollViewGroup2(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public InnerVerticalScrollViewGroup2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		child = getChildAt(0);
	}

	private float lastDispatchY;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "scrollY:" + getScrollY());
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				getParent().requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_MOVE:
				float dy = y - lastDispatchY;
				boolean isAtTopAndMoveUp = getScrollY() >= child.getTop() && dy < 0;
				boolean isAtBottomAndMoveDown = -getScrollY() >= getHeight() - child.getTop() - child.getHeight() && dy > 0;
				getParent().requestDisallowInterceptTouchEvent(!isAtTopAndMoveUp && !isAtBottomAndMoveDown);
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		lastDispatchY = y;
		return super.dispatchTouchEvent(ev);
	}

	private float lastY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				float dy = y - lastY;
				boolean isAtTopAndMoveUp = getScrollY() >= child.getTop() && dy < 0;
				boolean isAtBottomAndMoveDown = -getScrollY() >= getHeight() - child.getTop() - child.getHeight() && dy > 0;
				if(isAtTopAndMoveUp) {
					scrollTo(child.getLeft(), child.getTop());
				} else if(isAtBottomAndMoveDown){
					scrollTo(child.getLeft(), -(getHeight() - child.getTop() - child.getHeight()));
				} else {
					scrollBy(0, (int) -dy);
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		lastY = y;
		return true;
	}
}
