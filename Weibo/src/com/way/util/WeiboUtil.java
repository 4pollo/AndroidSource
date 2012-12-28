package com.way.util;

import com.tencent.weibo.api.StatusesAPI;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.oauthv2.OAuthV2Client;

public class WeiboUtil {
	public final static String CONSUMER_KEY = "801254708"; // appkey
	public final static String CONSUMER_SECRET = "072380ebce8f8f691cf3f690ea2b77a0"; // secret
	public final static String REDIRECT_URL = "http://www.hao123.com"; // url回调地址

	private final static String FORMAT = "json";
	private final static String CONTENTTYPE = "0";
	private final static String CLIENT_IP = "192.168.1.110";

	private OAuthV2 mAuthV2;

	private static WeiboUtil mWeiboManager;

	public static synchronized WeiboUtil getInstance() {
		if (mWeiboManager == null) {
			mWeiboManager = new WeiboUtil();
		}

		return mWeiboManager;
	}

	private WeiboUtil() {

		mAuthV2 = new OAuthV2(REDIRECT_URL);
		mAuthV2.setClientId(CONSUMER_KEY);
		mAuthV2.setClientSecret(CONSUMER_SECRET);

	}

	public String getRedictUrl() {
		return REDIRECT_URL;
	}

	public String getAppKey() {
		return CONSUMER_KEY;
	}

	// 获得AUTHO认证URL地址
	public String getAuthoUrl() {

		String urlStr = OAuthV2Client.generateImplicitGrantUrl(mAuthV2);

		return urlStr;
	}

	public void setAccesToakenString(String token) {
		if (token != null) {
			mAuthV2.setAccessToken(token);
		}

	}

	public void setOpenid(String openID) {
		if (openID != null) {
			mAuthV2.setOpenid(openID);
		}
	}

	public void setOpenKey(String openKey) {
		if (openKey != null) {
			mAuthV2.setOpenkey(openKey);
		}
	}

	public void setExpireIn(String expireIn) {
		if (expireIn != null) {
			mAuthV2.setExpiresIn(expireIn);
		}
	}

	public void setRefreshToken(String refreshToken) {
		if (refreshToken != null) {
			mAuthV2.setRefreshToken(refreshToken);
		}
	}

	public boolean parseAccessTokenAndOpenId(String data) {
		if (data != null) {
			return OAuthV2Client.parseAccessTokenAndOpenId(data, mAuthV2);
		}

		return false;
	}

	public String getAccessToken() {
		return mAuthV2.getAccessToken();
	}

	public String getOpenID() {
		return mAuthV2.getOpenid();
	}

	public String getOpenKey() {
		return mAuthV2.getOpenkey();

	}

	public String getExpireIn() {
		return mAuthV2.getExpiresIn();
	}

	public String getRefreshToken() {
		return mAuthV2.getRefreshToken();
	}

	// 获取用户资料
	public String getUserInfo() {
		String reString = null;
		UserAPI mUserAPI = new UserAPI(OAuthConstants.OAUTH_VERSION_2_A);
		try {
			reString = mUserAPI.info(mAuthV2, FORMAT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mUserAPI.shutdownConnection();

		return reString;
	}

	// 发送微博
	public String sendWeibo(String content) {
		String rString = null;
		TAPI mTapi = new TAPI(OAuthConstants.OAUTH_VERSION_2_A);
		try {

			rString = mTapi.add(mAuthV2, FORMAT, content, CLIENT_IP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mTapi.shutdownConnection();

		return rString;
	}

	/**
	 * 获取主页
	 * 
	 * @param pageflag
	 *            分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime
	 *            本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间， 向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-70条）
	 * @param type
	 *            拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评 <br>
	 *            如需拉取多个类型请使用|，如(0x1|0x2)得到3，此时type=3即可，填零表示拉取所有类型
	 * @return
	 */
	public String getHomeMsg(String pageflag, String pagetime, String reqnum) {
		String msg = null;
		StatusesAPI api = new StatusesAPI(OAuthConstants.OAUTH_VERSION_2_A);
		try {
			msg = api.homeTimeline(mAuthV2, FORMAT, pageflag, pagetime, reqnum,
					"0", CONTENTTYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 使用完毕后，请调用 shutdownConnection() 关闭自动生成的连接管理器
	 */
	public void shutdownConnection() {
		StatusesAPI api = new StatusesAPI(OAuthConstants.OAUTH_VERSION_2_A);
		api.shutdownConnection();
	}
}
