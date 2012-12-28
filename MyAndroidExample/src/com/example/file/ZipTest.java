package com.example.file;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ZipTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(this);
		setContentView(view);
		view.setBackgroundColor(R.color.white);
		TextView t = new TextView(this);
		view.addView(t);
		t.setText("这是个工具文件,直接调用就可以 "
				+ "工具类文件均在com.example.util 包下 .ZipUtils 是压缩与解压缩工具包" + " ");
	}
}
