package com.example.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class DialogTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(this);
		setContentView(view);
		Button button = new Button(this);
		button.setText("对话框的实现方式");
		view.addView(button);
		Button button1= new Button(this);
		button1.setText("android弹出窗口的实现（PopupWindow");
		view.addView(button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(DialogTest.this, DiaAllActivity.class);
				startActivity(intent);
			}
		});
//		button1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//			Intent intent = new Intent();
//			//intent.setClass(getApplicationContext(), .class);
//			startActivity(intent);
//			}
//		});
	}

}
