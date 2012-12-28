package com.example.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.widget.test1.Shortcuts;
import com.example.widget.test1.WidgetDemoTest;

public class WidgetTest extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(this);
		setContentView(view);
		Button button = new Button(getApplicationContext());
		button.setText("内存查看");
		view.addView(button);
		Button button1= new Button(getApplicationContext());
		button1.setText("倒计时");
		view.addView(button1);
		Button button2= new Button(getApplicationContext());
		button2.setText("创建快捷方式");
		view.addView(button2);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), memFloat.class);
			startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), WidgetDemoTest.class);
				startActivity(intent);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Shortcuts.class);
				startActivity(intent);
			}
		});
	}
	
}
