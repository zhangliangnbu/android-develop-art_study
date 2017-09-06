package com.tech.heathcilff.androiddevelopart.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * Created by zhangliang on 30/08/2017.
 */

public class BookDBOpenHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "book_db";
	public static final String BOOK_TABLE = "book";
	public static final String USER_TABLE = "user";
	private static final String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ BOOK_TABLE
			+ "(_id INTEGER PRIMARY KEY,"
			+ "name TEXT)";
	private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ USER_TABLE +
			"(_id INTEGER PRIMARY KEY," +
			"name TEXT," +
			"sex INT)";


	public BookDBOpenHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BOOK_TABLE);
		db.execSQL(CREATE_USER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
