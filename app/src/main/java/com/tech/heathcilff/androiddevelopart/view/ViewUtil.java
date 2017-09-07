package com.tech.heathcilff.androiddevelopart.view;

import android.view.MotionEvent;

/**
 *
 * Created by zhangliang on 06/09/2017.
 */

public class ViewUtil {
	public static String getActionName(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				return "down";
			case MotionEvent.ACTION_MOVE:
				return "move";
			case MotionEvent.ACTION_UP:
				return "up";
			case MotionEvent.ACTION_CANCEL:
				return "cancel";
			default:
				return event.getAction() + "";
		}
	}

	public static String getTouchTag(String tag, MotionEvent event) {
		return tag + "-" + getActionName(event);
	}
}
