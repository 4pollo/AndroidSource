package com.example.surfaceView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SurfaceTest extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		
		setContentView(view);
		//view.setLayoutParams();
		Button button = new Button(getApplicationContext());
		button.setText("surfaceview基本应用");
		view.addView(button);
		 
		Button button2= new Button(getApplicationContext());
		button2.setText("绘制矩形");
		view.addView(button2);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), TestSurfaceView.class);
				startActivity(intent);
			}
		});
		 
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Test.class);
				startActivity(intent);
			}
		});
		
		
	}
	
}
