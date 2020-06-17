package com.tech.heathcilff.androiddevelopart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * wrap-content
 * padding
 * custom attr
 * Created by zhangliang on 05/09/2017.
 */

public class CircleView extends View {
	private static final String TAG = "CircleView";
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static final int DEFAULT_WIDTH = 500, DEFAULT_HEIGHT = 500;

	public CircleView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public CircleView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		paint.setColor(Color.RED);
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
		Log.d(TAG, widthSpecMode + "/" + widthSpecSize + "/" + heightSpecMode + "/" + heightSpecSize);
		if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode) {
			setMeasuredDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		} else if(MeasureSpec.AT_MOST == widthSpecMode) {
			setMeasuredDimension(DEFAULT_WIDTH, heightSpecSize);
		} else if(MeasureSpec.AT_MOST == heightSpecMode) {
			setMeasuredDimension(widthSpecSize, DEFAULT_HEIGHT);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float width = getWidth() - getPaddingLeft() - getPaddingRight();
		float height = getHeight() - getPaddingTop() - getPaddingBottom();
		float r = Math.min(width, height) / 2;
		float cx = getPaddingLeft() + width / 2;
		float cy = getPaddingTop() + height / 2;
		canvas.drawCircle(cx, cy, r, paint);
	}
}
