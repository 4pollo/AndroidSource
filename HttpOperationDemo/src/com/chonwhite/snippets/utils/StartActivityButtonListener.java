package com.chonwhite.snippets.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartActivityButtonListener implements OnClickListener {
	Context currentContext;
	Class<? extends Activity> targetActivity;

	public StartActivityButtonListener(Context currentContext,Class<? extends Activity> targetActivity) {
		this.currentContext = currentContext;
		this.targetActivity = targetActivity;
	}

	@Override
	public void onClick(View v) {
		currentContext.startActivity(new Intent(currentContext, targetActivity));
	}

}