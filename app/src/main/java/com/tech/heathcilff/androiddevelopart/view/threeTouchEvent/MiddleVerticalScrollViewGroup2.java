package com.tech.heathcilff.androiddevelopart.view.threeTouchEvent;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tech.heathcilff.androiddevelopart.view.ViewUtil;

/**
 *
 * Created by zhangliang on 06/09/2017.
 */

public class MiddleVerticalScrollViewGroup2 extends FrameLayout {
	private static final String TAG = "middle";
	private ViewGroup child;
	private View childContent;
	public MiddleVerticalScrollViewGroup2(@NonNull Context context) {
		super(context);
	}

	public MiddleVerticalScrollViewGroup2(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public MiddleVerticalScrollViewGroup2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// 粗略如此写
		child = (ViewGroup) getChildAt(0);
		childContent = child.getChildAt(0);
	}

	private float lastDispatchY, lastDispatchX;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(ViewUtil.getTouchTag(TAG, ev), "dispatchTouchEvent-xy:" + ev.getX() + "/" + ev.getY());
		float y = ev.getY(), x = ev.getX();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				getParent().requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_MOVE:
				float dy = y - lastDispatchY;
				float dx = x - lastDispatchX;
				Log.d(TAG, "dxy:" + dx +"/" + dy);
				boolean isHorizontalScroll = Math.abs(dx) > Math.abs(dy);
				boolean disallowIntercept;
				if(isHorizontalScroll) {// 水平方向事件 传下去
					disallowIntercept = true;
				} else {
					boolean isAtTopAndMoveUp = getScrollY() >= child.getTop() && dy < 0;
					boolean isAtBottomAndMoveDown = -getScrollY() >= getHeight() - child.getTop() - child.getHeight() && dy > 0;
					disallowIntercept = !isAtTopAndMoveUp && !isAtBottomAndMoveDown;
				}
				Log.d(TAG, disallowIntercept + "");
				getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		lastDispatchX = x;
		lastDispatchY = y;
		return super.dispatchTouchEvent(ev);
	}

	float interceptX, interceptY;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(ViewUtil.getTouchTag(TAG, ev), "onInterceptTouchEvent-xy:" + ev.getX() + "/" + ev.getY());
		boolean intercept = false;
		float x = ev.getX(), y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				// 水平方向运动不拦截，其余拦截
				float dx = x - interceptX;
				float dy = y - interceptY;
				intercept = Math.abs(dx) <= Math.abs(dy);
				break;
			default:
				intercept = false;
				break;
		}

		interceptX = x;
		interceptY = y;
		lastY = y;// 事件被下级消费的话，需要在此处存储位置
		return intercept;
	}

	private float lastY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(ViewUtil.getTouchTag(TAG, event), "onTouchEvent-xy:" + event.getX() + "/" + event.getY());
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				float dy = y - lastY;
				boolean isAtTopAndMoveUp = getScrollY() >= child.getTop() && dy < 0;
				boolean isAtBottomAndMoveDown = -getScrollY() >= getHeight() - child.getTop() - child.getHeight() && dy > 0;
				if(isAtTopAndMoveUp) {
					scrollTo(0, child.getTop());
				} else if(isAtBottomAndMoveDown){
					scrollTo(0, -(getHeight() - child.getTop() - child.getHeight()));
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
