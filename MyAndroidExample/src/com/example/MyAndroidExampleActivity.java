package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.AlarmManager.AlarmTestActivity;
import com.example.GestureActivity.GestureTest;
import com.example.Notification.NotificationTest;
import com.example.ViewFlipper.ViewTest;
import com.example.button.ButtonTest;
import com.example.dialog.DialogTest;
import com.example.gallery.GalleryTest;
import com.example.gridview.GridviewTest;
import com.example.list.ListTest;
import com.example.menu.MenuTest;
import com.example.plasma.AnimActivity;
import com.example.progressDialog.ProgressDialogTest;
import com.example.slidingdrawer.SlidingDrawerTest;
import com.example.surfaceView.SurfaceTest;
import com.example.tabhost.TabTest;
import com.example.time.Timetest;
import com.example.tost.TostTest;
import com.example.turn.turntest;
import com.example.widget.WidgetTest;

public class MyAndroidExampleActivity extends Activity implements
		View.OnClickListener {

	Button b1 = null;
	Button b2 = null;
	Button b3 = null;
	Button b4 = null;
	Button b5 = null;
	Button b6 = null;
	Button b7 = null;
	Button b8 = null;
	Button b9 = null;
	Button b10 = null;
	Button b11 = null;
	Button b12 = null;
	Button b13 = null;
	Button b14 = null;
	Button b15 = null;
	Button b16 = null;
	Button b17 = null;
	Button b18 = null;
	Button b19 = null;
	Button b20 = null;
	
	Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
		b8.setOnClickListener(this);
		b9.setOnClickListener(this);
		b10.setOnClickListener(this);
		b11.setOnClickListener(this);
		b12.setOnClickListener(this);
		b13.setOnClickListener(this);
		b14.setOnClickListener(this);
		b15.setOnClickListener(this);
		b16.setOnClickListener(this);
		b17.setOnClickListener(this);
		b18.setOnClickListener(this);
		b19.setOnClickListener(this);
		b20.setOnClickListener(this);
	}

	public void init() {
		b1 = (Button) findViewById(R.id.b1);
		b2 = (Button) findViewById(R.id.b2);
		b3 = (Button) findViewById(R.id.b3);
		b4 = (Button) findViewById(R.id.b4);
		b5 = (Button) findViewById(R.id.b5);
		b6 = (Button) findViewById(R.id.b6);
		b7 = (Button) findViewById(R.id.b7);
		b8 = (Button) findViewById(R.id.b8);
		b9 = (Button) findViewById(R.id.b9);
		b10 = (Button) findViewById(R.id.b10);
		b11 = (Button) findViewById(R.id.b11);
		b12 = (Button) findViewById(R.id.b12);
		b13 = (Button) findViewById(R.id.b13);
		b14 = (Button) findViewById(R.id.b14);
		b15 = (Button) findViewById(R.id.b15);
		b16 = (Button) findViewById(R.id.b16);
		b17 = (Button) findViewById(R.id.b17);
		b18 = (Button) findViewById(R.id.b18);
		b19 = (Button) findViewById(R.id.b19);
		b20 = (Button) findViewById(R.id.b20);
		intent = new Intent();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.b1:
			intent.setClass(this, AlarmTestActivity.class);
			startActivity(intent);
			break;
		case R.id.b2:
			intent.setClass(getApplicationContext(), GestureTest.class);
			startActivity(intent);

			break;
		case R.id.b3:
			intent.setClass(getApplicationContext(), Timetest.class);
			startActivity(intent);
			
			break;
		case R.id.b4:
			intent.setClass(getApplicationContext(), ButtonTest.class);
			startActivity(intent);
			
			break;
		case R.id.b5:
			intent.setClass(getApplicationContext(), ListTest.class);
			startActivity(intent);
			
			break;
			 
		case R.id.b6:
			intent.setClass(getApplicationContext(), SurfaceTest.class);
			startActivity(intent);
			
			break;
			
		case R.id.b7:
			intent.setClass(getApplicationContext(), MenuTest.class);
			startActivity(intent);
			
			break;
		case R.id.b8:
			intent.setClass(getApplicationContext(), DialogTest.class);
			startActivity(intent);
			
			break;
		case R.id.b9:
			intent.setClass(getApplicationContext(), TostTest.class);
			startActivity(intent);
			
			break;
		case R.id.b10:
			intent.setClass(getApplicationContext(), ProgressDialogTest.class);
			startActivity(intent);
			
			break;
		case R.id.b11:
			intent.setClass(getApplicationContext(), TabTest.class);
			startActivity(intent);
			
			break;
		case R.id.b12:
			intent.setClass(getApplicationContext(), GridviewTest.class);
			startActivity(intent);
			
			break;
		case R.id.b13:
			intent.setClass(getApplicationContext(), GalleryTest.class);
			startActivity(intent);
			
			break;
		case R.id.b14:
			intent.setClass(getApplicationContext(), WidgetTest.class);
			startActivity(intent);
			
			break;
		case R.id.b15:
			intent.setClass(getApplicationContext(), NotificationTest.class);
			startActivity(intent);
			
			break;
		 
		case R.id.b16:
			intent.setClass(getApplicationContext(), ViewTest.class);
			startActivity(intent);
			
			break;
		case R.id.b17:
			intent.setClass(getApplicationContext(), SlidingDrawerTest.class);
			startActivity(intent);
			
			break;
		case R.id.b18:
			intent.setClass(getApplicationContext(), turntest.class);
			startActivity(intent);
			
			break;
		case R.id.b19:
			intent.setClass(getApplicationContext(), AnimActivity.class);
			startActivity(intent);
			
			break;
		case R.id.b20:
			intent.setClass(getApplicationContext(), MyAppDemo.class);
			startActivity(intent);
			
			break;
		default:
			break;
		}

	}

}