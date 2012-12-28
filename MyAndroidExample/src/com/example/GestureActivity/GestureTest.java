package com.example.GestureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.R;

public class GestureTest extends Activity implements OnClickListener {
	Button b = null;
	Button b1 = null;
	Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesture);
		init();
		b.setOnClickListener(this);
		b1.setOnClickListener(this);

	}

	public void init() {
		b = (Button) findViewById(R.id.GestureButton1);
		b1 = (Button) findViewById(R.id.GestureButton2);
		intent = new Intent();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.GestureButton1:
			intent.setClass(this, GestureActivity.class);
			startActivity(intent);

			break;
		case R.id.GestureButton2:
			intent.setClass(this, GestureActivity1.class);
			startActivity(intent);
			
			break;

		default:
			break;
		}
	}

}
