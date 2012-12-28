package com.example.handle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.downloader.MulThreadDownloader;

public class TestHandle extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		setContentView(view);
		view.setOrientation(1);
		Button button = new Button(this);
		button.setText("简单的handle应用");
		view.addView(button);
		Button button1 = new Button(this);
		button1.setText("多线程下载");
		view.addView(button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(getApplicationContext(), HandlerTest.class);
				startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(getApplicationContext(), MulThreadDownloader.class);
				startActivity(intent);
			}
		});
	}
}
