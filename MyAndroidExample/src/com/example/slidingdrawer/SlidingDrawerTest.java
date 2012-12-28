package com.example.slidingdrawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.slidingdrawer1.main;

public class SlidingDrawerTest extends Activity{

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		LinearLayout view = new LinearLayout(this);
		setContentView(view);
		Button button = new Button(this);
		button.setText("简单的抽屉效果");
		view.addView(button);
		Button button1 = new Button(this);
		button1.setText("自定义抽屉效果");
		view.addView(button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SlidingDrawerTest.this, SlidingDrawerDemoActivity.class);
				startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SlidingDrawerTest.this, main.class);
				startActivity(intent);
			}
		});
		
		
		
	}
	
}
