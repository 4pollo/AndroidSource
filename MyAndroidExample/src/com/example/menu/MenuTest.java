package com.example.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuTest extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(this);
		setContentView(view);
		Button button = new Button(this);
		button.setText("MENU菜单属性");
		view.addView(button);
		Button button1 = new Button(this);
		button1.setText("android 的上下文菜单");
		view.addView(button1);
		Button button2 = new Button(this);
		button2.setText("自定义menu");
		view.addView(button2);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(MenuTest.this, Menu1.class);
				startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(MenuTest.this, MenuActivity.class);
				startActivity(intent);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(MenuTest.this, TestTabMenu.class);
				startActivity(intent);
			}
		});
	}
	
}
