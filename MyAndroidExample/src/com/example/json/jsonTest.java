package com.example.json;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.xml.PraseXml;

public class jsonTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(this);
		setContentView(view);
		view.setOrientation(1);
		Button button = new Button(this);
		button.setText("json的解析");
		view.addView(button);
		Button button1 = new Button(this);
		button1.setText("xml的三种解析");
		view.addView(button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), JsonDemo.class);
				startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), PraseXml.class);
				startActivity(intent);
			}
		});
	}
}
