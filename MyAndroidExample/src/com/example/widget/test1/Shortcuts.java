package com.example.widget.test1;

import com.example.R;
import com.example.util.ShortcutUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Shortcuts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(getApplicationContext());
		setContentView(view);
		ShortcutUtil.createShortCut(Shortcuts.this, R.drawable.tu5503_5,
				R.string.app_name);

	}

}
