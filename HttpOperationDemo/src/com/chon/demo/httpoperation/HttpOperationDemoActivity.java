package com.chon.demo.httpoperation;

import android.app.Activity;
import android.os.Bundle;

import com.chonwhite.snippets.utils.StartActivityButtonListener;

public class HttpOperationDemoActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initUI();
	}

	private void initUI() {
		findViewById(R.id.toGetDemoActivityButton).setOnClickListener(
				new StartActivityButtonListener(this, GetDemoActivity.class));
		findViewById(R.id.toPostDemoActivityButton).setOnClickListener(
				new StartActivityButtonListener(this, PostDemoActivity.class));
		findViewById(R.id.toRssActivityButton).setOnClickListener(
				new StartActivityButtonListener(this, RssActivity.class));
		findViewById(R.id.toGetDrawableActivityButton)
				.setOnClickListener(
						new StartActivityButtonListener(this,
								GetDrawableActivity.class));
	}
}