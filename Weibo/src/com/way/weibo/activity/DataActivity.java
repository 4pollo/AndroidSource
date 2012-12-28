package com.way.weibo.activity;

import android.os.Bundle;
import android.widget.Toast;

public class DataActivity extends MyActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void isNetAvailable(boolean isWork) {
		// TODO Auto-generated method stub
		if (!isWork) {
			Toast.makeText(getApplicationContext(), "网络连接断开",
					Toast.LENGTH_SHORT).show();
		}
	}
}
