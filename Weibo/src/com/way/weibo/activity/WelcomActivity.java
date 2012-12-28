package com.way.weibo.activity;

import com.way.util.DefaultUserUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class WelcomActivity extends Activity {
	private LinearLayout logo;
	private DefaultUserUtil userUtil;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉状态栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcom_layout);

		userUtil = new DefaultUserUtil(this);
		logo = (LinearLayout) findViewById(R.id.welcom_logo);
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.welcom_logo);
		logo.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if (!userUtil.getUserId().equals("")) {
					Intent intent = new Intent(WelcomActivity.this,
							MainActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(WelcomActivity.this,
							AccountMangerActivity.class);
					startActivity(intent);
				}
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				finish();
			}
		});
	}

}
