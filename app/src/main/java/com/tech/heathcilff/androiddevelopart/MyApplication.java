package com.tech.heathcilff.androiddevelopart;

import android.app.ActivityManager;
import android.app.Application;
import android.os.Process;
import android.util.Log;

import java.util.List;

/**
 * Created by zhangliang on 29/08/2017.
 */

public class MyApplication extends Application {
	private static final String TAG = "MyApplication";
	@Override
	public void onCreate() {
		super.onCreate();

		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();

		Log.d(TAG, runningAppProcesses.size() + ":启动一个新进程啦");
		for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
			if(processInfo.pid == Process.myPid()) {
				Log.d(TAG, processInfo.processName);
				break;
			}
		}

	}


}
