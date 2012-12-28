package com.way.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.way.model.User;

public class DBhelper extends SQLiteOpenHelper {
	// 用来保存UserID、Access Token、Access Secret的表名
	private static final String DB_NAME = "weibo.db";
	public static final String TB_NAME = "users";

	public DBhelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME + " (" + User.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + User.USERID
				+ " TEXT," + User.ACCESS_TOKEN + " TEXT," + User.EXPIRES_IN
				+ " TEXT," + User.OPENID + " TEXT," + User.OPENKEY + " TEXT,"
				+ User.USERNAME + " TEXT," + User.USERICON + " blob" + ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("ALTER TABLE " + TB_NAME + " ADD COLUMN other TEXT");
	}
}
