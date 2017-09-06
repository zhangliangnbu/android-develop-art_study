package com.tech.heathcilff.androiddevelopart.view.sameDirectionTouch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 *
 * Created by zhangliang on 06/09/2017.
 */

public class InnerVerticalScrollViewGroup extends FrameLayout {
	private static final String TAG = "inner";
	public InnerVerticalScrollViewGroup(@NonNull Context context) {
		super(context);
	}

	public InnerVerticalScrollViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public InnerVerticalScrollViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	// position 没有在顶部或者底部则自己消费事件
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "XY:" + getX() + "/" + getY());
		Log.d(TAG, "scrollXY:" + getScrollX() + "/" + getScrollY());
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
//				getParent().requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_MOVE:

				break;
			case MotionEvent.ACTION_UP:
				break;
		}
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
				scrollBy(0, (int) -dy);
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		lastY = y;
		return true;
	}
}
