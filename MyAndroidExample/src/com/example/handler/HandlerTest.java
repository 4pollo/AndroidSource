package com.example.handler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HandlerTest extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(this);
		setContentView(view);
		TextView text=new TextView(getApplicationContext());
		view.addView(text);
		for(int i=0;;i++){
			text.setText(i);
		}
	}
}
