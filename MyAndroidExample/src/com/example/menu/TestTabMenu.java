package com.example.menu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.R;

public class TestTabMenu extends Activity {
	TabMenu.MenuBodyAdapter bodyAdapter = new TabMenu.MenuBodyAdapter(this,
			new int[] { R.drawable.small1, R.drawable.small2,
					R.drawable.small3, R.drawable.small4 });
	TabMenu tabMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablemenu);
		tabMenu = new TabMenu(this, new BodyClickEvent(), R.drawable.number9);// 出现与消失的动画
		tabMenu.update();
		tabMenu.SetBodyAdapter(bodyAdapter);
	}

	class BodyClickEvent implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			tabMenu.SetBodySelect(arg2, Color.GRAY);
			Log.i("Log", " BodyClickEvent implements OnItemClickListener "
					+ arg2);
		}

	}

	@Override
	/**
	 * 创建MENU
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");// 必须创建一项
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	/**
	 * 拦截MENU
	 */
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (tabMenu != null) {
			if (tabMenu.isShowing())
				tabMenu.dismiss();
			else {
				tabMenu.showAtLocation(findViewById(R.id.LinearLayout01),
						Gravity.BOTTOM, 0, 0);
			}
		}
		return false;// 返回为true 则显示系统menu
	}
}