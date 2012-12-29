package com.boyle.musicplayer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.boyle.model.GlobalModel;
import com.boyle.util.adapter.PullRefreshAdapter;
import com.boyle.utils.XMLParser;
import com.boyle.views.LoadStateView;
import com.boyle.views.PullRefreshListView;

public class MainActivity extends BaseActivity implements
		PullRefreshListView.OnRefreshLoadingMoreListener {

	// 所有的静态变量
	private PullRefreshListView musicListView;
	private PullRefreshAdapter musicAdapter;
	private LoadStateView loadStateView;
	
	private XMLParser parser = null;
	private NodeList nl = null;
	private int dataSize = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.main);
		
		// 给标题栏中的右边按钮增加单击事件
		getbtn_right().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.v("BE_DEBUG", "点击了标题栏中右边的按钮");
			}
		});

		// 获取载入层
		loadStateView = (LoadStateView) findViewById(R.id.LoadStatusBox);

		// 初始化列表数据
		new MyAsyncTask(this, GlobalModel.INIT_INDEX).execute();

		musicAdapter = new PullRefreshAdapter(MainActivity.this);
		musicListView = (PullRefreshListView) findViewById(R.id.list);
		musicListView.setOnRefreshListener(this);
	}

	// 下拉刷新
	@Override
	public void onRefresh() {
		new MyAsyncTask(this, GlobalModel.DRAG_INDEX).execute();
	}

	// 加载更多
	@Override
	public void onLoadMore() {
		new MyAsyncTask(this, GlobalModel.LOADMORE_INDEX).execute();
	}

	// 异步执行类
	private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
		private Context context;
		private int index;// 用于判断是下拉刷新还是点击加载更多

		public MyAsyncTask(Context context, int index) {
			this.context = context;
			this.index = index;
		}

		// 异步操作，第一步首先执行这里
		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			Log.i("BE_DEBUG", "---->onPreExecute");
			// 显示载入的动画效果
			loadStateView.startLoad();
		}

		// 异步操作，第二步执行这里
		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected Void doInBackground(Void... params) {
			Log.i("BE_DEBUG", "---->doInBackground");
			if (index == GlobalModel.DRAG_INDEX || index == GlobalModel.INIT_INDEX) {
				Log.d("BE_DEBUG", "---->Network loading...");
				parser = new XMLParser();
				String xml = parser.getXmlFromUrl(GlobalModel.URL); // 从网络获取XML
				Document doc = parser.getDomElement(xml); 			// 获取 DOM 节点
				nl = doc.getElementsByTagName(GlobalModel.KEY_SONG);
				dataSize = nl.getLength();
				Log.d("BE_DEBUG", "---->Network loaded!");
			}
			return null;
		}

		// 异步操作，第三步执行这里
		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(Void result) {
			Log.i("BE_DEBUG", "---->onPostExecute");
			super.onPostExecute(result);
			if (index == GlobalModel.DRAG_INDEX) {
				musicAdapter.clean(); // 清空所有数据
				loadData(); // 载入数据
				musicListView.onRefreshComplete();
			} else if (index == GlobalModel.LOADMORE_INDEX) {
				loadData();
			} else if (index == GlobalModel.INIT_INDEX) {
				loadData();
				musicListView.setAdapter(musicAdapter);
			}

			// 如果数据加载完毕，则隐藏按钮
			if (musicAdapter.getCount() == dataSize)
				musicListView.onLoadMoreComplete(true);
			else
				musicListView.onLoadMoreComplete(false);

			// 隐藏载入的动画效果
			loadStateView.stopLoad();
		}
	}

	private void loadData() {
		int count = musicAdapter.getCount();
		int startNumber = count + 1;
		int endNumber = (count + GlobalModel.PAGESIZE < this.dataSize) ? (count + GlobalModel.PAGESIZE)
				: this.dataSize;
		// 循环遍历所有的歌节点 <song>
		for (int i = startNumber - 1; i < endNumber; i++) {
			Element e = (Element) nl.item(i);
			String id = parser.getValue(e, GlobalModel.KEY_ID);
			String title = parser.getValue(e, GlobalModel.KEY_TITLE);
			String artist = parser.getValue(e, GlobalModel.KEY_ARTIST);
			String duration = parser.getValue(e, GlobalModel.KEY_DURATION);
			String thumburl = parser.getValue(e, GlobalModel.KEY_THUMB_URL);
			musicAdapter.addMusic(id, title, artist, duration, thumburl);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
