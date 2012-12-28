package com.example.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.R;

public class Activity01 extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridviewmain);

		//取得GridView对象
		GridView gridview = (GridView) findViewById(R.id.gridview);
		//添加元素给gridview
		gridview.setAdapter(new ImageAdapter(this));

		// 设置Gallery的背景
		gridview.setBackgroundResource(R.drawable.tu5503_4);

		//事件监听
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				Toast.makeText(Activity01.this, "你选择了" + (position + 1) + " 号图片", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
