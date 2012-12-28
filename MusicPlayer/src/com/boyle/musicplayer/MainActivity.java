package com.boyle.musicplayer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.boyle.util.adapter.LazyAdapter;
import com.boyle.utils.XMLParser;

public class MainActivity extends BaseActivity {
	// 所有的静态变量
	// XML文件地址,打开地址看一下
	static final String URL = "http://api.androidhive.info/music/music.xml";
	// XML 节点
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";
	// 设置单页载入的列数
	static final int PAGESIZE = 10;

	ListView list;
	LazyAdapter adapter;

	private View loadMoreView;
	private TextView loadMoreTextView;

	private XMLParser parser = null;
	private NodeList nl = null;
	private int dataSize = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.main);

		// 为左边的钮钮增加监听事件
		getbtn_right().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setTitle(R.string.strLoad);
				// 移除列表底部视图
				list.removeFooterView(loadMoreView);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						adapter.clean(); // 清空所有数据
						getData(); // 重新访问网络，获取数据
						loadData();// 载入列表
						adapter.notifyDataSetChanged();
						setTitle(R.string.g_strTitle);
						// 如果总记录大于10条，则显示底部视图
						if (dataSize > PAGESIZE)
							list.addFooterView(loadMoreView);// 设置列表底部视图
					}
				}, 1000);
			}
		});

		// 实现分页加载数据，一次加载10条数据
		loadMoreView = getLayoutInflater().inflate(R.layout.more, null);
		loadMoreTextView = (TextView) loadMoreView.findViewById(R.id.loadMoreButton);
		loadMoreTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadMoreTextView.setText(R.string.strLoad);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						loadData();
						adapter.notifyDataSetChanged();
						loadMoreTextView.setText(R.string.strMore);
						// 如果所有的记录选项等于数据集的条数，则移除列表底部视图
						if (adapter.getCount() == dataSize) {
							list.removeFooterView(loadMoreView);
						}
					}
				}, 100);
			}
		});

		adapter = new LazyAdapter(MainActivity.this);
		list = (ListView) findViewById(R.id.list);
		list.addFooterView(loadMoreView);// 设置列表底部视图
		// 将监听器的对象绑定到列对象上面
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里可以自由发挥，比如播放一首歌曲等等
			}
		});

		// 创建一个线程来处理下载文件操作
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = 0;
				getData();
				loadData(); // 获取歌曲列表
				handler.sendMessage(msg);
			}
		}).start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				list.setAdapter(adapter);// 对ListView进行内容填充
				break;
			}
		}
	};

	// 获取歌曲列表
	private void getData() {
		parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // 从网络获取XML
		Document doc = parser.getDomElement(xml); // 获取 DOM 节点
		nl = doc.getElementsByTagName(KEY_SONG);
		dataSize = nl.getLength();
	}

	private void loadData() {
		int count = adapter.getCount();
		int startNumber = count + 1;
		int endNumber = (count + PAGESIZE < this.dataSize) ? (count + PAGESIZE)
				: this.dataSize;
		// 循环遍历所有的歌节点 <song>
		for (int i = startNumber - 1; i < endNumber; i++) {
			Element e = (Element) nl.item(i);
			String id = parser.getValue(e, KEY_ID);
			String title = parser.getValue(e, KEY_TITLE);
			String artist = parser.getValue(e, KEY_ARTIST);
			String duration = parser.getValue(e, KEY_DURATION);
			String thumburl = parser.getValue(e, KEY_THUMB_URL);
			adapter.addMusic(id, title, artist, duration, thumburl);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
