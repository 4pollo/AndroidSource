package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActivityIntentDemo1 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		setContentView(view);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			setTitle("这个是activity1  " + bundle.getString("activity"));
		}
		Button button = new Button(getApplicationContext());
		button.setText("点我返回上一个activity");
		view.addView(button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("activity1", "这个是activity1传过来的数据");
				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(1, intent);
				finish();
			}
		});
	}
}
