package com.way.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 保存默认用户的工具类
 * 
 * @author way
 * 
 */
public class DefaultUserUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public DefaultUserUtil(Context context) {
		sp = context.getSharedPreferences("default_user", context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public void setUserId(String userId) {
		editor.putString("userId", userId);
		editor.commit();
	}

	public String getUserId() {
		return sp.getString("userId", "");
	}

	public void setName(String name) {
		editor.putString("name", name);
		editor.commit();
	}

	public String getName() {
		return sp.getString("name", "");
	}
}
