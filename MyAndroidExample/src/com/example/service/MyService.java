package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	static public String ServiceState="";
	@Override
	public IBinder onBind(Intent arg0) {
		Log.e("Service", "onBind");
		ServiceState="onBind";
		return null;
	}
	@Override
	public boolean onUnbind(Intent intent){
		super.onUnbind(intent);
		Log.e("Service", "onUnbind");
		ServiceState="onUnbind";
		return false;
		
	}
	@Override
	public void onCreate(){
		super.onCreate();
		Log.e("Service", "onCreate");
		ServiceState="onCreate";
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.e("Service", "onDestroy");
		ServiceState="onDestroy";
	}
	@Override
	public void onStart(Intent intent,int startid){
		super.onStart(intent, startid);
		Log.e("Service", "onStart");
		ServiceState="onStart";
	}

}
