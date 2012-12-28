package com.example.button;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ButtonTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view=new LinearLayout(this);
		Button button=new Button(this);
		setContentView(view);
		button.setText("button 按钮特效");
		view.addView(button);
		Button button1=new Button(this);
		button1.setText("buttonstate");
		view.addView(button1);
		Button button2=new Button(this);
		button2.setText("PathButtonActivity");
		view.addView(button2);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), PageFlipperActivity.class);
				startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ButtonState.class);
				startActivity(intent);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), PathButtonActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
	
}
