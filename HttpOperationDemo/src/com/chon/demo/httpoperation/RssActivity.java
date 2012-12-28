package com.chon.demo.httpoperation;

import java.io.IOException;
import java.util.ArrayList;

import com.chon.demo.rssreader.RssHandler;
import com.chon.demo.rssreader.RssItem;
import com.chon.httpoperation.GetOperation;
import com.chon.httpoperation.HandledResult;
import com.chon.httpoperation.OperationListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RssActivity extends Activity {

	private OperationListener listener = new OperationListener(){

		@Override
		public void onError(long arg0, Bundle arg1, Exception e) {
			setTitle("Exception " + e);
		}

		@Override
		public void onError(long arg0, Bundle arg1, IOException e) {
			setTitle("IOException " + e);
		}

		@Override
		public void onNotOkay(long arg0, Bundle arg1, int code, String arg3) {
			setTitle("Oh Oh " + code);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void onResult(long arg0, Bundle arg1, HandledResult result) {
			System.out.println("size:" + result.results.size());
			setTitle("Loading Complete");
			mAdapter.setData((ArrayList<RssItem>) result.results);
		}
		
	};
	private MyApplication mApplication;
	private ListView rssListView;
	private RssAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mApplication = (MyApplication) this.getApplication();
		initUI();
		
	}
	
	private void initUI(){
		this.setContentView(R.layout.rss);
		this.rssListView = (ListView)findViewById(R.id.rssListView);
		rssListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(mAdapter.getData().get(position).getLink()));
				it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				startActivity(it);
			}
		});
		mAdapter = new RssAdapter(this.getLayoutInflater());
		rssListView.setAdapter(mAdapter);
		findViewById(R.id.button).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				loadRss();
			}
		});
	};
	
	void loadRss(){
		setTitle("Loading.....");
		String url = "http://rss.sina.com.cn/news/marquee/ddt.xml";
		GetOperation getOperation = new GetOperation(10,url,
				RssHandler.class, listener );
		mApplication.request(getOperation);
	}
	
}
