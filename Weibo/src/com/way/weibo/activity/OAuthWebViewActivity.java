package com.way.weibo.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.way.model.User;
import com.way.util.ImageUtil;
import com.way.util.WeiboUtil;
import com.way.util.db.UserDB;

/**
 * 授权Activity，同时保存用户信息,根据腾讯api类改写的
 * 
 * @author way
 * 
 */
public class OAuthWebViewActivity extends MyActivity {
	public final static int RESULT_CODE = 2;
	private static final String TAG = "OAuthWebViewActivity";
	private View progressBar;
	private WebView mWebView;
	private WeiboUtil util;
	private UserDB userDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("用户授权");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_layout);
		initView();// 初始化view
		initData();// 初始化数据
	}

	private void initView() {
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.requestFocus();

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		mWebView.requestFocus();
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

		progressBar = findViewById(R.id.show_request_progress_bar);
	}

	private void initData() {
		userDB = new UserDB(this);
		util = WeiboUtil.getInstance();
		String urlStr = util.getAuthoUrl();
		CookieSyncManager.createInstance(this);
		mWebView.loadUrl(urlStr);
		Log.i(TAG, urlStr);

		WebViewClient client = new WebViewClient() {
			/**
			 * 回调方法，当页面开始加载时执行
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				showProgress();
				Log.i(TAG, "WebView onPageStarted...");
				Log.i(TAG, "URL = " + url);
				if (url.indexOf("access_token=") != -1) {
					int start = url.indexOf("access_token=");
					String responseData = url.substring(start);
					if (util.parseAccessTokenAndOpenId(responseData)) {
						try {
							// 这四个信息一定要保存，用来以后验证用户的，不过access_token有效期只有7天
							// 所以需要开一个闹钟，在过期前执行更新
							String access_token = util.getAccessToken();
							String expires_in = util.getExpireIn();
							String openID = util.getOpenID();
							String openKey = util.getOpenKey();
							Log.i(TAG, "access_token = " + access_token
									+ "\nexpires_in = " + expires_in
									+ "\nopenID = " + openID + "\nopenKey = "
									+ openKey);

							String userInfo = util.getUserInfo();// 返回的用户json信息
							JSONObject data = new JSONObject(userInfo)
									.getJSONObject("data");// 解析json信息，保存有用信息
							String headUrl = null;
							if (data.getString("head") != null
									&& !"".equals(data.getString("head"))) {
								headUrl = data.getString("head") + "/100";// 获取用户头像路径
							}
							String userId = data.getString("name");// 获取用户id，
							String userName = data.getString("nick");// 获取用户昵称

							User user = new User();// 生成一个user对象保存到数据库
							user.setUserId(userId);
							user.setUserName(userName);
							user.setAccess_token(access_token);
							user.setExpires_in(expires_in);
							user.setOpenID(openID);
							user.setOpenKey(openKey);

							Long insertId = 0L;
							if (userDB.HaveUserInfo(userId)) {// 如果该用户存在，就只更新昵称和头像
								userDB.UpdateUserInfo(userName, ImageUtil
										.getRoundBitmapFromUrl(headUrl, 10),
										userId);
							} else {// 如果不存在，就保存完整用户
								insertId = userDB.SaveUserInfo(user, ImageUtil
										.getRoundBitmapFromUrl(headUrl, 10));
							}
							Log.i(TAG, user.toString());

						} catch (Exception e) {
							e.printStackTrace();
						}

						setResult(RESULT_CODE);// 返回账户管理界面
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "授权出现未知错误",
								Toast.LENGTH_SHORT).show();
					}
					view.destroyDrawingCache();// 清理缓存
					view.destroy();
				}
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				hideProgress();// 在这里结束进度条显示
				super.onPageFinished(view, url);
			}

			/*
			 * TODO Android2.2及以上版本才能使用该方法
			 * 目前https://open.t.qq.com中存在http资源会引起sslerror，待网站修正后可去掉该方法
			 */
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				if ((null != view.getUrl())
						&& (view.getUrl().startsWith("https://open.t.qq.com"))) {
					handler.proceed();// 接受证书
				} else {
					handler.cancel(); // 默认的处理方式，WebView变成空白页
				}
				// handleMessage(Message msg); 其他处理
			}
		};
		mWebView.setWebViewClient(client);

	}

	/**
	 * 显示进度条
	 */
	private void showProgress() {
		runOnUiThread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				progressBar.setVisibility(View.VISIBLE);
			}
		});

	}

	/**
	 * 隐藏进度条
	 */
	private void hideProgress() {
		runOnUiThread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				progressBar.setVisibility(View.INVISIBLE);
			}
		});

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
