package com.way.weibo.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	private TabHost m_tabHost;
	private RadioGroup m_radioGroup;
	public static String mTextviewArray[] = { "主页", "提及", "广场", "私信", "资料" };

	public static Class mTabClassArray[] = { HomeActivity.class,
			AtActivity.class, SearchActivity.class, MessageActivity.class,
			DataActivity.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);
		init();
	}

	private void init() {
		m_tabHost = getTabHost();

		int count = mTabClassArray.length;
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = m_tabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(mTextviewArray[i])
					.setContent(getTabItemIntent(i));
			m_tabHost.addTab(tabSpec);
		}

		m_radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		m_radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_tab_main:

					m_tabHost.setCurrentTabByTag(mTextviewArray[0]);
					break;
				case R.id.main_tab_at:
					m_tabHost.setCurrentTabByTag(mTextviewArray[1]);
					break;
				case R.id.main_tab_search:
					m_tabHost.setCurrentTabByTag(mTextviewArray[2]);
					break;
				case R.id.main_tab_message:
					m_tabHost.setCurrentTabByTag(mTextviewArray[3]);
					break;
				case R.id.main_tab_data:
					m_tabHost.setCurrentTabByTag(mTextviewArray[4]);
					break;
				}
			}
		});

		((RadioButton) m_radioGroup.getChildAt(0)).toggle();
	}

	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent(this, mTabClassArray[index]);

		return intent;
	}
}
