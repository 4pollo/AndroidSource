package com.way.weibo.activity;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.way.model.User;
import com.way.util.DefaultUserUtil;
import com.way.util.WeiboUtil;
import com.way.util.adapter.MyListViewAdapter;
import com.way.util.db.UserDB;
import com.way.util.view.MyListView;
import com.way.util.view.MyListView.OnLoadMoreListener;
import com.way.util.view.MyListView.OnMyScrollListener;
import com.way.util.view.MyListView.OnRefreshListener;

public class HomeActivity extends MyActivity implements OnClickListener,
		OnItemLongClickListener, OnItemClickListener {
	private static final String TAG = "way";
	private static final int REFRESH_STATE = 0x002;// 开始刷新的状态
	private static final String PAGE_SIZE = "20";// 每页加载的条数
	private static final String PAGE_FIRST = "0";// 第一次加载数据标志
	private static final String PAGE_NEXT = "1";// 加载更多，即向下翻页
	private static final String PAGE_PRE = "2";// 向上翻页，暂时没用到
	private String lastPageTime;// 上一次请求返回的最后一条记录时间
	private String firstPageTime;// 上一次请求返回的第一条记录时间,暂时没用到
	private MyListViewAdapter adapter;
	private TextView refresh, name, write;
	private View netErr;
	private ImageView popup;
	private ImageView topScrolling;
	private MyListView myListView;
	private PopupWindow mPopupWindow;
	private View[] mPopupBtn;
	private Animation animation;
	private LinkedList<JSONObject> list;
	private UserDB db;
	private WeiboUtil util;
	private DefaultUserUtil userUtil;
	private int visibleLastIndex = 0; // 最后的可视项索引

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		initData();
		initView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		new MyRefreshTask().execute();
	}

	private void initView() {
		netErr = findViewById(R.id.home_net_error);
		animation = AnimationUtils.loadAnimation(this, R.anim.refresh_rotate);
		refresh = (TextView) findViewById(R.id.home_refresh);
		name = (TextView) findViewById(R.id.home_name);
		name.setText(userUtil.getName());
		write = (TextView) findViewById(R.id.home_write);
		popup = (ImageView) findViewById(R.id.home_popup_img);
		topScrolling = (ImageView) findViewById(R.id.home_layout_top_scrolling);
		topScrolling.setVisibility(View.GONE);
		topScrolling.setOnClickListener(this);
		popup.setImageResource(R.drawable.wb_item_explo);
		myListView = (MyListView) findViewById(R.id.home_listView);
		myListView.setFocusable(true);
		refresh.setOnClickListener(this);
		name.setOnClickListener(this);
		write.setOnClickListener(this);
		popup.setOnClickListener(this);
		initPopupWin();
		list = new LinkedList<JSONObject>();
		adapter = new MyListViewAdapter(this, list, myListView);
		myListView.setAdapter(adapter);
		// 每个item的长按事件，弹出评论的view
		myListView.setOnItemLongClickListener(this);
		// 每个item的点击事件，进入item的详细信息
		myListView.setOnItemClickListener(this);
		// 下拉刷新
		myListView.setOnRefreshListener(new OnRefreshListener() {

			public void OnRefresh() {
				new MyRefreshTask().execute();
			}

		});
		// 加载更多按钮点击
		myListView.setOnLoadMoreListener(new OnLoadMoreListener() {

			public void OnLoadMore() {
				new MyNextTask()
						.execute(new String[] { PAGE_NEXT, lastPageTime });
			}
		});
		// 滚动监听,为了滚动到最后自动加载数据
		myListView.setOnMyScrollListener(new OnMyScrollListener() {

			public void onMyScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
				int lastIndex = itemsLastIndex + 1 + 1; // 加上底部的loadMoreView项和头部下拉刷新的view
				// Log.i("way", lastIndex + " 最后一项引标");

				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& visibleLastIndex == lastIndex) {
					// 在这里异步加载翻页数据
					// Log.i("way", "loading...");
					new MyNextTask().execute(new String[] { PAGE_NEXT,
							lastPageTime });
				}

			}

			public void OnMyScroll(AbsListView view, int firstVisiableItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				visibleLastIndex = firstVisiableItem + visibleItemCount - 1;
				if (visibleLastIndex >= 6) {
					topScrolling.setVisibility(View.VISIBLE);
				} else {
					topScrolling.setVisibility(View.GONE);
				}
				// Log.i("way", visibleLastIndex + " 可见的最后一项");
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		userUtil = new DefaultUserUtil(this);
		db = new UserDB(this);
		List<User> list = db.GetUserList(true);
		User u = UserDB.getUserByuserId(userUtil.getUserId(), list);// 从配置文件中读取
		util = WeiboUtil.getInstance();
		util.setAccesToakenString(u.getAccess_token());
		util.setExpireIn(u.getExpires_in());
		util.setOpenid(u.getOpenID());
		util.setOpenKey(u.getOpenKey());
	}

	/**
	 * 点击事件处理
	 */
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.home_refresh:// 刷新图标
			new MyRefreshTask().execute();
			Toast.makeText(getApplicationContext(), "刷新", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.home_name:// 弹出popupWindow
		case R.id.home_popup_img:
			if (!mPopupWindow.isShowing()) {
				mPopupWindow.showAsDropDown(name, -50, 0);
				popup.setImageResource(R.drawable.wb_item_explo_up);
			}
			Toast.makeText(getApplicationContext(), "我的名字", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.home_write:// 进入发表界面
			Intent i = new Intent(this, WriteWeiboActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			Toast.makeText(getApplicationContext(), "写心情", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.home_layout_top_scrolling:
			myListView.setSelection(0);// 返回顶部
			break;
		default:
			break;
		}
	}

	private void initPopupWin() {
		View view = LayoutInflater.from(this)
				.inflate(R.layout.home_popup, null);
		mPopupWindow = new PopupWindow(view, 150, LayoutParams.WRAP_CONTENT);
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.wb_bg_cfloat));
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);

		mPopupBtn = new View[5];
		mPopupBtn[0] = view.findViewById(R.id.home_popup_all);
		mPopupBtn[1] = view.findViewById(R.id.home_popup_confirm);
		mPopupBtn[2] = view.findViewById(R.id.home_popup_between);
		mPopupBtn[3] = view.findViewById(R.id.home_popup_friend);
		mPopupBtn[4] = view.findViewById(R.id.home_popup_famous);

		mPopupBtn[0].setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				name.setText(userUtil.getName());
				Toast.makeText(getApplicationContext(), "全部",
						Toast.LENGTH_SHORT).show();

			}
		});

		mPopupBtn[1].setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				name.setText("认证用户");
				Toast.makeText(getApplicationContext(), "认证用户",
						Toast.LENGTH_SHORT).show();
			}
		});
		mPopupBtn[2].setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				name.setText("相互收听");
				Toast.makeText(getApplicationContext(), "相互收听",
						Toast.LENGTH_SHORT).show();
			}
		});
		mPopupBtn[3].setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				name.setText("QQ好友");
				Toast.makeText(getApplicationContext(), "QQ好友",
						Toast.LENGTH_SHORT).show();
			}
		});
		mPopupBtn[4].setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				name.setText("名人");
				Toast.makeText(getApplicationContext(), "名人",
						Toast.LENGTH_SHORT).show();
			}
		});
		// 弹出窗口消失监听
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			public void onDismiss() {
				// TODO Auto-generated method stub
				popup.setImageResource(R.drawable.wb_item_explo);
			}
		});
	}

	/**
	 * 第一次加载和下拉刷新的异步任务
	 * 
	 * @author way
	 * 
	 */
	public class MyRefreshTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			myListView.changeHeaderViewByState(REFRESH_STATE);
			myListView.updateLoadMoreViewState(REFRESH_STATE);
			refresh.startAnimation(animation);
		}

		@Override
		protected String doInBackground(Void... params) {
			String result = util.getHomeMsg(PAGE_FIRST, PAGE_FIRST, PAGE_SIZE);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					JSONObject dataObj = new JSONObject(result)
							.getJSONObject("data");
					JSONArray array = dataObj.getJSONArray("info");
					if (array != null && array.length() > 0) {
						list.clear();
						int lenth = array.length();
						for (int i = 0; i < lenth; i++) {
							list.add(array.optJSONObject(i));
						}
					}
					firstPageTime = list.get(0).getString("timestamp");// 得到第一项的时间
					lastPageTime = list.get(list.size() - 1).getString(
							"timestamp");// 得到最后一项的时间
					adapter.notifyDataSetChanged();
					myListView.onRefreshComplete();
					myListView.onLoadMoreComplete(false);
					refresh.clearAnimation();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				myListView.onRefreshComplete();
				myListView.onLoadMoreComplete(false);
				refresh.clearAnimation();
				Toast.makeText(getApplicationContext(), "获取数据失败,请重试",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.Close();
	}

	@Override
	public void isNetAvailable(boolean isWork) {
		// TODO Auto-generated method stub
		if (!isWork) {
			netErr.setVisibility(View.VISIBLE);
			Toast.makeText(getApplicationContext(), "网络连接断开",
					Toast.LENGTH_SHORT).show();
		} else {
			netErr.setVisibility(View.GONE);
		}
	}

	/**
	 * 加载更多的异步任务
	 * 
	 * @author way
	 * 
	 */
	public class MyNextTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			myListView.updateLoadMoreViewState(REFRESH_STATE);
			refresh.startAnimation(animation);
		}

		@Override
		protected String doInBackground(String... params) {
			String result = util.getHomeMsg(params[0], params[1], PAGE_SIZE);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					JSONObject dataObj = new JSONObject(result)
							.getJSONObject("data");
					JSONArray array = dataObj.getJSONArray("info");
					int size = list.size();// 已有item的总数

					if (size >= 60) {// 如果长度太长，移除头20项，防止内存溢出
						for (int i = 0; i < 20; ++i)
							list.remove(i);
						size = list.size();// 移除之后，重新获取一些item的总数
					}
					if (array != null && array.length() > 0) {
						int lenth = array.length();
						for (int i = 0; i < lenth; i++) {
							list.addLast(array.optJSONObject(i));
						}
					}
					firstPageTime = list.get(0).getString("timestamp");// 得到第一项的时间
					lastPageTime = list.get(list.size() - 1).getString(
							"timestamp");// 得到最后一项的时间
					adapter.notifyDataSetChanged();
					myListView.setSelection(size);// 选择项
					myListView.onLoadMoreComplete(false);
					refresh.clearAnimation();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				myListView.onLoadMoreComplete(false);
				refresh.clearAnimation();
				Toast.makeText(getApplicationContext(), "获取数据失败,请重试",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * item的长按事件，弹出评论窗口
	 */
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * item的点击事件处理，进入item的详细界面
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		try {
			Intent i = new Intent(HomeActivity.this, WeiboDetailActivity.class);
			i.putExtra("weiboId", list.get(position).getString("id"));// 把id传递过去，重新获取数据
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
