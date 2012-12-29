package com.boyle.model;

// 这里定义常量
public class GlobalModel {
	// XML文件地址,打开地址看一下
	public static final String URL = "http://api.androidhive.info/music/music.xml";
	// XML 节点
	public static final String KEY_SONG = "song"; // parent node
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_ARTIST = "artist";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_THUMB_URL = "thumb_url";
	// 设置单页载入的列数
	public static final int PAGESIZE = 10;
	// 设置操作标识
	public final static int INIT_INDEX = 0;// 初始化标识
	public final static int DRAG_INDEX = 1;// 下拉刷新标识
	public final static int LOADMORE_INDEX = 2;// 加载更多标识
}
