package com.example;

 
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.activity.ActivityTest;
import com.example.broadcastreceiver.Broadtest;
import com.example.file.FileTest;
import com.example.handle.TestHandle;
import com.example.json.jsonTest;
import com.example.service.ServiceTest;

public class MyAppDemo extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		setContentView(view);
		view.setOrientation(1);
		Button button = new Button(getApplicationContext());
		button.setText("android开发基础");
		view.addView(button);
		Button button1 = new Button(getApplicationContext());
		button1.setText("android service");
		view.addView(button1);
		Button button2 = new Button(getApplicationContext());
		button2.setText("android handle");
		view.addView(button2);
		Button button3 = new Button(getApplicationContext());
		button3.setText("文件处理");
		view.addView(button3);
		Button button4 = new Button(getApplicationContext());
		button4.setText("xml/json/html");
		view.addView(button4);
		Button button5 = new Button(getApplicationContext());
		button5.setText("Broadcast Reciver");
		view.addView(button5);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ActivityTest.class);
				startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent = new Intent ();
			intent.setClass(getApplicationContext(),ServiceTest.class);
			startActivity(intent);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(getApplicationContext(),TestHandle.class);
				startActivity(intent);
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(getApplicationContext(),FileTest.class);
				startActivity(intent);
			}
		});
		button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(getApplicationContext(),jsonTest.class);
				startActivity(intent);
			}
		});
		button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(getApplicationContext(),Broadtest.class);
				startActivity(intent);
			}
		});

	}
}
