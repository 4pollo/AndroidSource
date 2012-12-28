package com.way.util;

import android.content.Context;

import com.way.util.db.UserDB;

public class UserDBUtil {
	private static UserDB userDB;

	public static UserDB getInstance(Context context) {
		if (userDB == null) {
			userDB = new UserDB(context);
		}
		return userDB;
	}
}
