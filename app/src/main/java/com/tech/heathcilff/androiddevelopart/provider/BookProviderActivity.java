package com.tech.heathcilff.androiddevelopart.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tech.heathcilff.androiddevelopart.R;

public class BookProviderActivity extends AppCompatActivity {
	private static final String TAG = "BookProviderActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_provider);

//		Uri uri = Uri.parse("content://" + BookContentProvider.AUTHORITIES);
//		getContentResolver().query(uri, null, null, null, null);
//		getContentResolver().query(uri, null, null, null, null);
//		getContentResolver().query(uri, null, null, null, null);

		insertBook();
		queryBooks();



	}

	private void insertBook() {
		ContentValues values = new ContentValues();
		values.put("_id", 10);
		values.put("name", "Go");
		getContentResolver().insert(BookContentProvider.BOOK_CONTENT_URI, values);
	}

	private void queryBooks() {
		Cursor cursor = getContentResolver().query(BookContentProvider.BOOK_CONTENT_URI,
				null, null, null, null);
		while (cursor.moveToNext()) {
			Log.d(TAG, "book:" + cursor.getInt(0) + "-" + cursor.getString(1));
		}
	}

}
