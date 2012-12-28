package com.way.weibo.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.way.model.User;
import com.way.util.DefaultUserUtil;
import com.way.util.db.UserDB;

/**
 * 帐号管理界面
 * 
 * @author way
 * 
 */
public class AccountMangerActivity extends MyActivity implements
		OnItemClickListener, OnItemLongClickListener, OnClickListener {
	private final static String TAG = "AccountMangerActivity";
	private UserDB userDB;
	private DefaultUserUtil userUtil;
	private List<User> userList;
	private ListView listView;
	private ImageView user_default_headicon;
	private TextView account_add_btn_bar;
	private UserAdapater adapter;
	private TextView currentAccount;
	private AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.account);
		initView();
		initData();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.account_listView);
		user_default_headicon = (ImageView) findViewById(R.id.user_default_headicon);
		account_add_btn_bar = (TextView) findViewById(R.id.account_add_btn_bar);
		currentAccount = (TextView) findViewById(R.id.account_cur_account);
		user_default_headicon.setOnClickListener(this);
		account_add_btn_bar.setOnClickListener(this);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		builder = new AlertDialog.Builder(this);
	}

	private void initData() {
		userUtil = new DefaultUserUtil(this);
		userDB = new UserDB(this);
		userList = userDB.GetUserList(true);
		adapter = new UserAdapater();
		listView.setAdapter(adapter);
		String userId = userUtil.getUserId();
		if (userList.size() > 0) {
			if (!userId.equals("")) {
				user_default_headicon.setImageDrawable(UserDB.getUserByuserId(
						userId, userList).getUserIcon());// 加载用户头像
				currentAccount.setText(UserDB.getUserByuserId(userId, userList)
						.getUserId());// 加载用户名称
			} else {
				user_default_headicon.setImageDrawable(userList.get(0)
						.getUserIcon());// 加载数据库第一个人头像
				currentAccount.setText(userList.get(0).getUserId());// 加载数据库第一个人名称
			}
		} else {
			user_default_headicon
					.setImageResource(R.drawable.wb_head_default_180);
		}
	}

	/**
	 * 适配器
	 * 
	 * @author way
	 * 
	 */
	public class UserAdapater extends BaseAdapter {
		public int getCount() {
			return userList.size();
		}

		public Object getItem(int position) {
			return userList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.account_list_item, null);
			}
			ImageView user_headicon = (ImageView) convertView
					.findViewById(R.id.user_headicon);
			TextView user_nick = (TextView) convertView
					.findViewById(R.id.user_nick);
			TextView user_name = (TextView) convertView
					.findViewById(R.id.user_name);
			User user = userList.get(position);
			user_headicon.setImageDrawable(user.getUserIcon());
			user_nick.setText(user.getUserName());
			user_name.setText(user.getUserId());
			return convertView;
		}
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			final int position, long arg3) {
		// TODO Auto-generated method stub
		builder.setTitle("提示").setMessage("确定删除该帐号？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						userDB.DelUserInfo(userList.get(position).getUserId());
						userList = userDB.GetUserList(true);
						adapter.notifyDataSetChanged();
					}
				}).setNegativeButton("取消", null).create().show();
		return false;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		user_default_headicon
				.setImageDrawable(userList.get(arg2).getUserIcon());
		currentAccount.setText(userList.get(arg2).getUserName());

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.account_add_btn_bar:
			Intent intent = new Intent(AccountMangerActivity.this,
					OAuthWebViewActivity.class);
			startActivityForResult(intent, 1);// 跳转到腾讯的微博授权页面,使用webview来显示
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

			break;
		case R.id.user_default_headicon:
			if (userList.size() > 0) {
				String userId = currentAccount.getText().toString();
				userUtil.setName(UserDB.getUserByuserId(userId, userList)
						.getUserName());
				userUtil.setUserId(userId);
				gotoMainActivity();
				Toast.makeText(getApplicationContext(), "进入主界面",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "请先添加帐号",
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode == OAuthWebViewActivity.RESULT_CODE) {
				Toast.makeText(getApplicationContext(), "授权成功",
						Toast.LENGTH_SHORT).show();
				userList = userDB.GetUserList(true);
				adapter.notifyDataSetChanged();
				user_default_headicon.setImageDrawable(userList.get(0)
						.getUserIcon());// 加载默认头像
				currentAccount.setText(userList.get(0).getUserId());
			}
		}
	}

	private void gotoMainActivity() {
		Intent intent = new Intent(AccountMangerActivity.this,
				MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	// 退出
	private void exit() {
		builder.setTitle("帐号管理").setMessage("确定要退出吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				}).setNegativeButton("取消", null).create().show();
	}

	@Override
	public void onBackPressed() {
		exit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		userDB.Close();
	}

	@Override
	public void isNetAvailable(boolean isWork) {
		// TODO Auto-generated method stub
		if (!isWork) {
			Toast.makeText(getApplicationContext(), "网络连接断开",
					Toast.LENGTH_SHORT).show();
		}
	}
}
