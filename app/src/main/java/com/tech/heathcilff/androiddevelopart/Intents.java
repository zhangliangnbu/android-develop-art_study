package com.tech.heathcilff.androiddevelopart;

import android.content.Context;
import android.content.Intent;

import com.tech.heathcilff.androiddevelopart.aidl.BookManagerActivity;
import com.tech.heathcilff.androiddevelopart.messenger.MessengerActivity;
import com.tech.heathcilff.androiddevelopart.provider.BookProviderActivity;

/**
 *
 * Created by zhangliang on 24/08/2017.
 */

public class Intents {
	public static Intent toSecondActivity(Context context) {
		return new Intent(context, SecondActivity.class);
	}
	public static Intent toThirdActivity(Context context) {
		return new Intent(context, ThirdActivity.class);
	}

	public static Intent toFourthActivity(Context context) {
		return new Intent(context, FourthActivity.class);
	}

	public static Intent toFifthActivity(Context context) {
		return new Intent(context, FifthIPCActivity.class);
	}

	public static Intent toSixthhActivity(Context context) {
		return new Intent(context, SixthIPCActivity.class);
	}

	public static Intent toBookManagerActivity(Context context) {
		return new Intent(context, BookManagerActivity.class);
	}

	public static Intent toMessengerActivity(Context context) {
		return new Intent(context, MessengerActivity.class);
	}
	public static Intent toBookProviderPage(Context context) {
		return new Intent(context, BookProviderActivity.class);
	}



}
