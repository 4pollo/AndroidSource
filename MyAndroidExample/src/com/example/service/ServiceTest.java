package com.example.service;
 

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ServiceTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		setContentView(view);
		view.setOrientation(1);
		Button button = new Button(this);
		button.setText("service");
		view.addView(button);
		Button button1 = new Button(this);
		button1.setText("service简单的音乐播放");
		view.addView(button1);
		Button button2 = new Button(this);
		button2.setText("基于Service与ContentProvider的音乐播放实例");
		view.addView(button2);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), TestService.class);
				startActivity(intent);
			}
		});
	 
	}
}
