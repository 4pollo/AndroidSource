package com.way.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID = "_id";
	public static final String USERID = "userId";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String EXPIRES_IN = "expires_in";
	public static final String OPENID = "openID";
	public static final String OPENKEY = "openKey";
	public static final String USERNAME = "userName";
	public static final String USERICON = "userIcon";

	private String userId;// 用户id
	private String access_token;
	private String expires_in;
	private String openID;
	private String openKey;
	private String userName;
	private Drawable userIcon;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getOpenKey() {
		return openKey;
	}

	public void setOpenKey(String openKey) {
		this.openKey = openKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Drawable getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(Drawable userIcon) {
		this.userIcon = userIcon;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", access_token=" + access_token
				+ ", expires_in=" + expires_in + ", openID=" + openID
				+ ", openKey=" + openKey + ", userName=" + userName
				+ ", userIcon=" + userIcon + "]";
	}
}
