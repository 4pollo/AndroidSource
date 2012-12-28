package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActivityTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		setContentView(view);
		Button button = new Button(getApplicationContext());
		button.setText("activity的生命周期");
		view.addView(button);
		Button button1 = new Button(getApplicationContext());
		button1.setText("activity传值、回调");
		view.addView(button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), ActivityDemo.class);
			startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ActivityIntentDemo.class);
				startActivity(intent);
			}
		});
		
	}
}
