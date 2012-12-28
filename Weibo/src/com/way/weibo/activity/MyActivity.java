package com.way.weibo.activity;

import com.way.util.NetWorkUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public abstract class MyActivity extends Activity {
	private BroadcastReceiver newWorkReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			isNetAvailable(NetWorkUtil.isNetworkAvailable(context));// 传递给子类是否掉线
		}
	};

	public abstract void isNetAvailable(boolean isWork);//回调函数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
		//intentFilter.addAction("android.net.wifi.STATE_CHANGE");
		//intentFilter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
		registerReceiver(newWorkReceiver, intentFilter);// 注册接受消息广播
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(newWorkReceiver);
	}
}
