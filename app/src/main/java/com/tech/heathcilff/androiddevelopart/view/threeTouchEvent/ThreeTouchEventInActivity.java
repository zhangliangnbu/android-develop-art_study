package com.tech.heathcilff.androiddevelopart.view.threeTouchEvent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tech.heathcilff.androiddevelopart.R;

// inner view 使用内部拦截法 出现问题 middle事件会被cancel掉
public class ThreeTouchEventInActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_three_touch_event);
	}
}
// log如下:
//      纯粹使用内部拦截法：middle内部请求outer是否拦截， inner内部请求middle是否拦截，会出现问题，如下：
//		9-07 10:06:55.717 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-down: dispatchTouchEvent-xy:457.0/158.0
//		09-07 10:06:55.718 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-down: onInterceptTouchEvent-xy:457.0/158.0
//		09-07 10:06:55.718 12904-12904/com.tech.heathcilff.androiddevelopart D/middle-down: dispatchTouchEvent-xy:457.0/158.0
//		09-07 10:06:55.718 12904-12904/com.tech.heathcilff.androiddevelopart D/middle-down: onInterceptTouchEvent-xy:457.0/158.0
//		09-07 10:06:55.719 12904-12904/com.tech.heathcilff.androiddevelopart D/inner-down: dispatchTouchEvent-xy:457.0/158.0
//		09-07 10:06:55.719 12904-12904/com.tech.heathcilff.androiddevelopart D/inner-down: onTouchEvent-xy:457.0/158.0
//		Down事件。outer和middle都没有拦截，且都被请求不要拦截，事件被inner消耗
//
//		09-07 10:06:55.843 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: dispatchTouchEvent-xy:457.0/192.0
//		09-07 10:06:55.843 12904-12904/com.tech.heathcilff.androiddevelopart D/middle-move: dispatchTouchEvent-xy:457.0/192.0
//		09-07 10:06:55.843 12904-12904/com.tech.heathcilff.androiddevelopart D/middle: dxy:0.0/34.0
//		09-07 10:06:55.844 12904-12904/com.tech.heathcilff.androiddevelopart D/inner-move: dispatchTouchEvent-xy:457.0/192.0
//		09-07 10:06:55.844 12904-12904/com.tech.heathcilff.androiddevelopart D/inner: dXY:0.0/34.0
//		09-07 10:06:55.845 12904-12904/com.tech.heathcilff.androiddevelopart D/inner-move: onTouchEvent-xy:457.0/192.0
//		Move事件第一次。outer和middle已经被请求不要拦截所以，所以没有执行onInterceptTouchEvent()，在inner dispatchTouchEvent()会请求middle拦截事件
//
//		09-07 10:06:55.859 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: dispatchTouchEvent-xy:457.0/208.0
//		09-07 10:06:55.859 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: onInterceptTouchEvent-xy:457.0/208.0
//		09-07 10:06:55.861 12904-12904/com.tech.heathcilff.androiddevelopart D/middle-cancel: dispatchTouchEvent-xy:457.0/208.0
//		09-07 10:06:55.861 12904-12904/com.tech.heathcilff.androiddevelopart D/middle-cancel: onInterceptTouchEvent-xy:457.0/208.0
//		09-07 10:06:55.861 12904-12904/com.tech.heathcilff.androiddevelopart D/inner-cancel: dispatchTouchEvent-xy:457.0/208.0
//		09-07 10:06:55.861 12904-12904/com.tech.heathcilff.androiddevelopart D/inner-cancel: onTouchEvent-xy:457.0/208.0
//		Move事件第二次。为什么outer会执行onInterceptTouchEvent()？猜想可能因为在上次的Move事件中nner请求middle拦截事件，会导致mFirstTouchTarget为空，所有的父控件都没有目标。于是intercept直接为true，继而调用onInterceptTouchEvent()方法。
//		outer拦截事件之后，middle和inner会获得cancel事件。
//
//		09-07 10:06:55.875 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: dispatchTouchEvent-xy:457.0/229.0
//		09-07 10:06:55.875 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: onInterceptTouchEvent-xy:457.0/229.0
//		09-07 10:06:55.892 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: dispatchTouchEvent-xy:457.0/245.51437
//		09-07 10:06:55.893 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: onInterceptTouchEvent-xy:457.0/245.51437
//		09-07 10:06:55.909 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: dispatchTouchEvent-xy:452.0/263.54538
//		09-07 10:06:55.909 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: onInterceptTouchEvent-xy:452.0/263.54538
//		09-07 10:06:55.926 12904-12904/com.tech.heathcilff.androiddevelopart D/outer-move: dispatchTouchEvent-xy:448.8288/278.3697

