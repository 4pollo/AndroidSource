package com.way.util.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.way.model.User;

/**
 * 用户数据库操作类
 * 
 * @author way
 * 
 */
public class UserDB {
	private DBhelper dbHelper;
	private SQLiteDatabase db;

	public UserDB(Context context) {
		dbHelper = new DBhelper(context);
		db = dbHelper.getReadableDatabase();
	}

	/**
	 * 关闭数据库
	 */
	public void Close() {
		db.close();
		dbHelper.close();
	}

	/**
	 * 获取所有用户信息
	 * 
	 * @param isAll
	 *            是否要获取全部信息，包括头像和用户名
	 * @return 用户信息数组
	 */
	public List<User> GetUserList(Boolean isAll) {
		// 用来存放用户资料的list
		List<User> userList = new ArrayList<User>();
		Cursor cursor = db.query(DBhelper.TB_NAME, null, null, null, null,
				null, User.ID + " DESC");
		while (cursor.moveToNext()) {
			User user = new User();
			user.setUserId(cursor.getString(1));
			user.setAccess_token((cursor.getString(2)));
			user.setExpires_in((cursor.getString(3)));
			user.setOpenID(cursor.getString(4));
			user.setOpenKey(cursor.getColumnName(5));
			if (isAll) {
				user.setUserName(cursor.getString(6));
				ByteArrayInputStream stream = new ByteArrayInputStream(
						cursor.getBlob(7));
				Drawable icon = Drawable.createFromStream(stream, "image");
				user.setUserIcon(icon);
			}
			userList.add(user);
		}
		cursor.close();
		return userList;
	}

	/**
	 * 判断users表中的是否包含某个UserID的记录
	 * 
	 * @param UserId
	 *            用户id
	 * @return 是否存在
	 */
	public Boolean HaveUserInfo(String UserId) {
		Boolean isExist = false;
		Cursor cursor = db.query(DBhelper.TB_NAME, null, User.USERID + "=?",
				new String[] { UserId }, null, null, null);
		isExist = cursor.moveToFirst();
		Log.i("HaveUserInfo", isExist.toString());
		cursor.close();
		return isExist;
	}

	/**
	 * 更新users表的记录，根据UserId更新用户名和用户头像
	 * 
	 * @param userName
	 *            用户名
	 * @param userIcon
	 *            用户头像
	 * @param UserId
	 *            用户id
	 * @return 插入数据库成功的条数
	 */
	public int UpdateUserInfo(String userName, Bitmap userIcon, String UserId) {
		ContentValues values = new ContentValues();
		values.put(User.USERNAME, userName);
		// BLOB类型
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// 将Bitmap压缩成PNG编码，质量为100%存储
		userIcon.compress(Bitmap.CompressFormat.PNG, 100, os);
		// 构造SQLite的Content对象，这里也可以使用raw
		values.put(User.USERICON, os.toByteArray());
		int id = db.update(DBhelper.TB_NAME, values, User.USERID + "=?",
				new String[] { UserId });
		Log.i("UpdateUserInfo2", id + "");
		return id;
	}

	/**
	 * 更新users表的记录,不包含用户名和头像，当ACCESS_TOKEN过时 需要重新更新
	 * 
	 * @param user
	 *            需要更新的用户
	 * @return 插入数据库成功的条数
	 */
	public int UpdateUserInfo(User user) {
		ContentValues values = new ContentValues();
		values.put(User.USERID, user.getUserId());
		values.put(User.ACCESS_TOKEN, user.getAccess_token());
		values.put(User.EXPIRES_IN, user.getExpires_in());
		values.put(User.OPENID, user.getOpenID());
		values.put(User.OPENKEY, user.getOpenKey());
		int id = db.update(DBhelper.TB_NAME, values,
				User.USERID + "=" + user.getUserId(), null);
		Log.i("UpdateUserInfo", id + "");
		return id;
	}

	/**
	 * 添加users表的记录,不包括用户名和头像
	 * 
	 * @param user
	 *            需要保存的用户对象
	 * @return 插入数据库成功的条数
	 */
	public Long SaveUserInfo(User user) {
		ContentValues values = new ContentValues();
		values.put(User.USERID, user.getUserId());
		values.put(User.ACCESS_TOKEN, user.getAccess_token());
		values.put(User.EXPIRES_IN, user.getExpires_in());
		values.put(User.OPENID, user.getOpenID());
		values.put(User.OPENKEY, user.getOpenKey());
		Long uid = db.insert(DBhelper.TB_NAME, User.ID, values);
		Log.i("SaveUserInfo", uid + "");
		return uid;
	}

	/**
	 * 添加users表的记录，包括用户名和头像信息
	 * 
	 * @param user
	 *            用户对象
	 * @param icon
	 *            用户头像，位图
	 * @return 插入数据库成功的条数
	 */
	public Long SaveUserInfo(User user, Bitmap icon) {
		ContentValues values = new ContentValues();
		values.put(User.USERID, user.getUserId());
		values.put(User.USERNAME, user.getUserName());
		values.put(User.ACCESS_TOKEN, user.getAccess_token());
		values.put(User.EXPIRES_IN, user.getExpires_in());
		values.put(User.OPENID, user.getOpenID());
		values.put(User.OPENKEY, user.getOpenKey());
		if (icon != null) {
			// BLOB类型
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			// 将Bitmap压缩成PNG编码，质量为100%存储
			icon.compress(Bitmap.CompressFormat.PNG, 100, os);
			// 构造SQLite的Content对象，这里也可以使用raw
			values.put(User.USERICON, os.toByteArray());
		}
		Long uid = db.insert(DBhelper.TB_NAME, User.ID, values);
		Log.i("SaveUserInfo", uid + "");
		return uid;
	}

	/**
	 * 删除users表的记录,根据用户id
	 * 
	 * @param UserId
	 *            用户id
	 * @return 删除成功的条数
	 */
	public int DelUserInfo(String UserId) {
		int id = db.delete(DBhelper.TB_NAME, User.USERID + "=?",
				new String[] { UserId });
		Log.i("DelUserInfo", id + "");
		return id;
	}

	/**
	 * 通过用户id获取用户信息
	 * 
	 * @param userId
	 *            用户id
	 * @param userList
	 *            传入的用户数组
	 * @return 指定的用户对象
	 */
	public static User getUserByuserId(String userId, List<User> userList) {
		User user = null;
		int size = userList.size();
		for (int i = 0; i < size; i++) {
			if (userId.equals(userList.get(i).getUserId())) {
				user = userList.get(i);
				break;
			}
		}
		return user;
	}
}
