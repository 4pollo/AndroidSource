package com.way.weibo.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class AtActivity extends MyActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.at_layout);
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
