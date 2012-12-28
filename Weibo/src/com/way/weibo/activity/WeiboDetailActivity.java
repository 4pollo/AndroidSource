package com.way.weibo.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class WeiboDetailActivity extends MyActivity {
	private ListView listView;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_detail_head);
		// initView();
	}

	private void initView() {
		inflater = LayoutInflater.from(this);
		View head = inflater.inflate(R.layout.home_detail_head, null);
		listView = (ListView) findViewById(R.id.home_detail_listView);
		listView.addHeaderView(head);
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
