package com.tech.heathcilff.androiddevelopart.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class BookContentProvider extends ContentProvider {
	private static final String TAG = "BookContentProvider";
	public static final String AUTHORITIES = "com.tech.heathcliff.androiddevelopart.book_provider";
	public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/book");
	public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");
	private static final int BOOK_CONTENT_CODE = 0;
	private static final int USER_CONTENT_CODE = 1;
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		uriMatcher.addURI(AUTHORITIES, "book", BOOK_CONTENT_CODE);
		uriMatcher.addURI(AUTHORITIES, "user", USER_CONTENT_CODE);
	}
	private String getTableName(Uri uri) {
		switch (uriMatcher.match(uri)) {
			case BOOK_CONTENT_CODE:
				return BookDBOpenHelper.BOOK_TABLE;
			case USER_CONTENT_CODE:
				return BookDBOpenHelper.USER_TABLE;
			default:
				throw new IllegalArgumentException("unknown uri");
		}
	}
	private SQLiteDatabase db;

	public BookContentProvider() {
	}

	@Override
	public boolean onCreate() {
		Log.d(TAG, "onCreate 线程: " + Thread.currentThread().getName());
		initDB();
		return true;
	}

	private void initDB() {
		db = new BookDBOpenHelper(getContext()).getWritableDatabase();
		db.execSQL("delete from " + BookDBOpenHelper.BOOK_TABLE);
		db.execSQL("delete from " + BookDBOpenHelper.USER_TABLE);
		db.execSQL("insert into " + BookDBOpenHelper.BOOK_TABLE + " values(3, 'Java')");
		db.execSQL("insert into " + BookDBOpenHelper.BOOK_TABLE + " values(5, 'C++')");
		db.execSQL("insert into " + BookDBOpenHelper.BOOK_TABLE + " values(6, 'Python')");
		db.execSQL("insert into " + BookDBOpenHelper.USER_TABLE + " values(1, 'Jim', 0)");
		db.execSQL("insert into " + BookDBOpenHelper.USER_TABLE + " values(8, 'Lily', 1)");
		db.execSQL("insert into " + BookDBOpenHelper.USER_TABLE + " values(9, 'Lucy', 1)");
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int i = db.delete(getTableName(uri), selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return i;
	}

	@Override
	public String getType(Uri uri) {
		return "*/*";
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		db.insert(getTableName(uri), null, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return uri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
	                    String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "query 线程: " + Thread.currentThread().getName());
		return db.query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder, null);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
	                  String[] selectionArgs) {
		return db.update(getTableName(uri), values, selection, selectionArgs);
	}
}
