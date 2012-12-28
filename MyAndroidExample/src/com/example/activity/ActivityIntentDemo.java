package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
 

public class ActivityIntentDemo extends Activity {
	private static final String TAG = "ActivityDemo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		setContentView(view);
		setTitle("这是activity");
		Button button = new Button(getApplicationContext());
		button.setText("点我跳到下一个Activity");
		view.addView(button);
		System.out.println("风电发电示范斯蒂芬斯蒂芬");
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						ActivityIntentDemo1.class);
				intent.putExtra("activity", "我来自activity");
				startActivityForResult(intent, 1);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println(requestCode+"requestCode");
		System.out.println(resultCode+"resultCode");
		if(requestCode==resultCode){
			Bundle bundle= data.getExtras();
			String str=bundle.getString("activity1");
			setTitle(str);
		}
		
		
		
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "start onStart~~~");
	}

	// 当按HOME键时，然后再次启动应用时，我们要恢复先前状态
	@Override
	protected void onRestart() {
		super.onRestart();
	 
		Log.e(TAG, "start onRestart~~~");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "start onResume~~~");
	}

	// 当我们按HOME键时，我在onPause方法里，将输入的值赋给mString
	@Override
	protected void onPause() {
		super.onPause();
	 
		Log.e(TAG, "start onPause~~~");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "start onStop~~~");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "start onDestroy~~~");
	}

}
