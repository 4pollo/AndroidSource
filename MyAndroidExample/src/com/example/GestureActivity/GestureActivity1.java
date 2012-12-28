package com.example.GestureActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.R;

public class GestureActivity1 extends Activity implements OnTouchListener,
		OnGestureListener {
	private GestureDetector mGestureDetector;

	
		


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesture1);
		mGestureDetector = new GestureDetector(this);
		Button tv = (Button) findViewById(R.id.Gesturebutton3);
		tv.setOnTouchListener(this);
//		tv.setFocusable(true);
//		tv.setClickable(true);
//		tv.setLongClickable(true);
//		mGestureDetector.setIsLongpressEnabled(true);
	}

	/*
	 * 在onTouch()方法中，我们调用GestureDetector的onTouchEvent()方法，
	 * 将捕捉到的MotionEvent交给GestureDetector 来分析是否有合适的callback函数来处理用户的手势
	 */
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	// 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
	public boolean onDown(MotionEvent arg0) {

		Toast.makeText(this, "onDown", Toast.LENGTH_SHORT).show();
		return true;
	}

	/*
	 * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
	 * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
	 */
	public void onShowPress(MotionEvent e) {

		Toast.makeText(this, "onShowPress", Toast.LENGTH_SHORT).show();
	}

	// 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
	public boolean onSingleTapUp(MotionEvent e) {

		Toast.makeText(this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
		return true;
	}

	// 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		Toast.makeText(this, "onFling", Toast.LENGTH_LONG).show();
		return true;
	}

	// 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

		Toast.makeText(this, "onScroll", Toast.LENGTH_LONG).show();
		return true;
	}

	// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
	public void onLongPress(MotionEvent e) {

		Toast.makeText(this, "onLongPress", Toast.LENGTH_LONG).show();
	}
}
