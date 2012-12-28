package com.example.tabhost;

 

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.R;

public class TabMenuActivity extends Activity {
	/** Called when the activity is first created. */
	
	TabView dockView;
	TextView tv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabmain1);
		tv = (TextView) findViewById(R.id.tv);
		dockView = new TabView(this);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {//监听menu键
			if(dockView.isOpen()) {
				tv.setText("点击menu键打开dock");
				
				dockView.closeDock();//关闭dock
			}
			else {
				tv.setText("再次点击menu键或者back键关闭dock");
				dockView.openDock();//打开dock
			}
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {//监听back键
			if(dockView.isOpen()) {//当dock打开时，按back键关闭dock
				dockView.closeDock();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	

}