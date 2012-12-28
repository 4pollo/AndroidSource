package com.example.widget.test1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WidgetDemoTest extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		setContentView(view);
		TextView textview = new TextView(getApplicationContext());
		textview.setText("返回桌面长按桌面，然后添加widget 最后选择这个项目");
		view.addView(textview);
		
		
	}
	
}
