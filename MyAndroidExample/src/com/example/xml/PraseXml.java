package com.example.xml;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.R;

public class PraseXml extends Activity {
	/** Called when the activity is first created. */

	private ListView lv;
	private String[] tvText = new String[] { "dom解析", "sax解析", "pull解析"};

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return tvText.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater
					.from(PraseXml.this);
			View v_list = inflater.inflate(R.layout.xmllist_item, null);
			TextView tv = (TextView) v_list.findViewById(R.id.tv);
			tv.setText(tvText[position]);
			return v_list;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xmlmain);

		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new MyAdapter());

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String temp = "com.xsmm.util.";
				switch (position) {
				case 0:
					temp += "DomPrase";
					break;
				case 1:
					temp += "SaxPrase";
					break;
				case 2:
					temp += "PullPrase";
					break;
				}
				forward(temp);
			}
		});
	}

	private void forward(String className) {
		Bundle bundle = new Bundle();
		bundle.putString("clazz", className);
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(PraseXml.this, PraseResult.class);
		startActivity(intent);
	}
}