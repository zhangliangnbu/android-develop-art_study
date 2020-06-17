package com.tech.heathcilff.androiddevelopart;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.heathcilff.androiddevelopart.socket.TCPClientActivity;
import com.tech.heathcilff.androiddevelopart.view.sameDirectionTouch.SameDirectionTouchActivity2;
import com.tech.heathcilff.androiddevelopart.view.threeTouchEvent.ThreeTouchEventExActivity;
import com.tech.heathcilff.androiddevelopart.view.threeTouchEvent.ThreeTouchEventInActivity;

import java.util.List;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private static final String CATEGORY_SAMPLE = "com.tech.heathcilff.androiddevelopart.sample.SAMPLE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_main);
		UserManager.sUserId ++;
		Log.d(TAG, UserManager.sUserId + "");

		View view = findViewById(R.id.tv_toSecond);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(), SecondActivity.class));
			}
		});

		findViewById(R.id.btn_toSecond).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("androiddevelopart.action.second");
				intent.putExtra(Intent.EXTRA_TEXT, "hello world");
				toSMS();
			}
		});

		findViewById(R.id.btn_toFifth).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = Intents.toFifthActivity(getApplicationContext());
				intent.putExtra("string", "ABC");
				Bundle bundle = new Bundle();
				bundle.putString("bundle", "bundle ha ha");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn_toSixth).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toSixthhActivity(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toServer).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toBookManagerActivity(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toMessengerActivity).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toMessengerActivity(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toBookProviderPage).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(Intents.toBookProviderPage(getApplicationContext()));
			}
		});

		findViewById(R.id.btn_toTCPClientPage).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), TCPClientActivity.class));
			}
		});

		// 二重事件冲突
		findViewById(R.id.btn_toViewPage).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), SameDirectionTouchActivity2.class));
			}
		});

		// 三重事件冲突 内部拦截法
		findViewById(R.id.btn_toThreeTouchEventPage).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ThreeTouchEventInActivity.class));
			}
		});

		// 三重事件冲突 外部拦截发(都在中间层中处理逻辑，部分外部拦截法)
		findViewById(R.id.btn_toThreeTouchEventExPage).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ThreeTouchEventExActivity.class));
			}
		});

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_ac);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(new ResolveInfoAdapter(this, getAllSampleActivities()));
	}

	private List<ResolveInfo> getAllSampleActivities() {
		Intent filter = new Intent();
		filter.setAction(Intent.ACTION_RUN);
		filter.addCategory(CATEGORY_SAMPLE);
		return getPackageManager().queryIntentActivities(filter, 0);
	}

	private void onRouteClicked(ResolveInfo route) {
		ActivityInfo activity = route.activityInfo;
		ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
		startActivity(new Intent(Intent.ACTION_VIEW).setComponent(name));
	}

	private void toSMS() {
		Intent intent = new Intent(Intent.ACTION_SENDTO);// action
		intent.setData(Uri.parse("smsto:10086"));// data
		intent.putExtra("sms_body", "手头有点紧，借点钱吧~~");
		if(intent.resolveActivity(getPackageManager()) != null){
			startActivity(intent);
		}
	}

	private void startActivityByShowChooser() {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setDataAndType(Uri.parse("content://bac"), "text/plain");
		// Always use string resources for UI text.
		// This says something like "Share this photo with"
		String title = "Share this with ...";
		// Create intent to show the chooser dialog
		Intent chooser = Intent.createChooser(sendIntent, title);

		// Verify the original intent will resolve to at least one activity
		if (sendIntent.resolveActivity(getPackageManager()) != null) {
			startActivity(chooser);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(TAG, "onRestoreInstanceState");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	class ResolveInfoAdapter extends RecyclerView.Adapter<ResolveInfoAdapter.ResolveInfoViewHolder> {

		private final PackageManager pm;
		private final LayoutInflater inflater;
		private final List<ResolveInfo> samples;

		private ResolveInfoAdapter(Context context, List<ResolveInfo> resolveInfos) {
			this.samples = resolveInfos;
			this.inflater = LayoutInflater.from(context);
			this.pm = context.getPackageManager();
		}

		@Override
		public ResolveInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			View view = inflater.inflate(R.layout.item_route, viewGroup, false);
			return new ResolveInfoViewHolder(view);
		}

		@Override
		public void onBindViewHolder(ResolveInfoViewHolder viewHolder, int i) {
			ResolveInfo item = samples.get(i);
			viewHolder.textView.setText(item.loadLabel(pm));
		}

		@Override
		public int getItemCount() {
			return samples.size();
		}

		class ResolveInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

			public final TextView textView;

			public ResolveInfoViewHolder(View view) {
				super(view);
				this.textView = (TextView) view.findViewById(android.R.id.text1);
				view.setOnClickListener(this);
			}

			@Override
			public void onClick(@NonNull View v) {
				onRouteClicked(samples.get(getAdapterPosition()));
			}
		}
	}


}
