package com.example.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.gallery.test1.Guide;
import com.example.gallery.test2.GalleryFlowActivity;
import com.example.gallery.test3.testImageView;

public class GalleryTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		view.setOrientation(1);
		setContentView(view);
		Button button= new Button(this);
		button.setText("绚丽的自定义Gallery");
		view.addView(button);
		Button button1= new Button(this);
		button1.setText("图片滚动");
		view.addView(button1);
		Button button2= new Button(this);
		button2.setText("3D效果");
		view.addView(button2);
		Button button3= new Button(this);
		button3.setText("图片浏览");
		view.addView(button3);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Guide.class);
				startActivity(intent);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), GalleryFlowActivity.class);
				startActivity(intent);
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), testImageView.class);
				startActivity(intent);
			}
		});
		
		
	}
	
}
