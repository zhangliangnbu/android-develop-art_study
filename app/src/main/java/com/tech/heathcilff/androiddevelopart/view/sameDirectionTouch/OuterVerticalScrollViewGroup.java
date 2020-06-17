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
 * 外部拦截法
 * 竖直方向 内部和外部滑动冲突问题
 * 1.滑动限制。InnerViewGroup 和 OutViewGroup的滑动事件皆为上下移动，且内容不能移出到窗口之外
 * 2,冲突规则。优先移动InnerViewGroup的内容, 待其不能移动时, 让OutViewGroup拦截事件即:
 * InnerViewGroup的内容在最高点且往上移动或在最低点且往下移动时，OutViewGroup拦截事件.
 * Created by zhangliang on 06/09/2017.
 */

public class OuterVerticalScrollViewGroup extends FrameLayout {
	private static final String TAG = "outer";
	private InnerVerticalScrollViewGroup innerViewGroup;
	private View innerChild;
	public OuterVerticalScrollViewGroup(@NonNull Context context) {
		super(context);
	}

	public OuterVerticalScrollViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public OuterVerticalScrollViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		innerViewGroup = (InnerVerticalScrollViewGroup) getChildAt(0);
		innerChild = innerViewGroup.getChildAt(0);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "XY:" + getX() + "/" + getY());
		Log.d(TAG, "scrollXY:" + getScrollX() + "/" + getScrollY());
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean intercept =false;
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				intercept = false;
				break;
			case MotionEvent.ACTION_MOVE:
				boolean isInnerAtTopAndMoveUp = innerViewGroup.getScrollY() - innerChild.getTop() >= 0 && y - lastY < 0;
				boolean isInnerAtBottomAndMoveDown = -innerViewGroup.getScrollY() >= innerViewGroup.getHeight() - innerChild.getTop() - innerChild.getHeight() && y - lastY > 0;
				intercept = isInnerAtTopAndMoveUp || isInnerAtBottomAndMoveDown;
				break;
			case MotionEvent.ACTION_UP:
				intercept = false;
				break;
			default:
				break;
		}
		lastY = y;
		return intercept;
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
				boolean isAtTopAndMoveUp = getScrollY() >= innerViewGroup.getTop() && dy < 0;
				boolean isAtBottomAndMoveDown = -getScrollY() >= getHeight() - innerViewGroup.getTop() - innerViewGroup.getHeight() && dy > 0;
				if(!isAtTopAndMoveUp && !isAtBottomAndMoveDown) {
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
