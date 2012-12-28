package com.example.downloader.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{
	private static final String DBNAME = "download.db";
	private static final int VERSION = 2;

	public DBOpenHelper(Context context) {
		super(context,DBNAME,null,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS filedown(id integer primary key autoincrement,downpath varchar(100),threadid INTEGER,position INGEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS filedown");
		onCreate(db);
	}

}
