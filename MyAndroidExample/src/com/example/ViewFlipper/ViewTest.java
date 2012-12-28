package com.example.ViewFlipper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ViewFlipper1.ViewFlipperActivity;
import com.example.Viewpager.TestViewPager;

public class ViewTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		setContentView(view);
		view.setOrientation(1);
		Button button = new Button(getApplicationContext());
		button.setText("android实现uc和墨迹天气那样的左右拖动效果");
		view.addView(button);
		Button button1 = new Button(getApplicationContext());
		button1.setText("viewpager框架");
		view.addView(button1);
		Button button2= new Button(getApplicationContext());
		button2.setText("Android手势识别ViewFlipper触摸动画");
		view.addView(button2);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						FlingGalleryActivity.class);
				startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						TestViewPager.class);
				startActivity(intent);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						ViewFlipperActivity.class);
				startActivity(intent);
			}
		});
	}

}
