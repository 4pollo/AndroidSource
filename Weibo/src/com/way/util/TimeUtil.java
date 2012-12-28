package com.way.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author way
 * 
 */
public class TimeUtil {

	public static String converTime(long time) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - time ;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > 24 * 2 * 60 * 60) {// 2天以上就返回标准时间
			timeStr = getTime(time);
		} else if (timeGap > 24 * 60 * 60) {// 1天-2天
			timeStr = timeGap / (24 * 60 * 60) + "天前";
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			timeStr = timeGap / (60 * 60) + "小时前";
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
		return format.format(new Date(time*1000));
	}

	public static String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
		Date date = new Date(System.currentTimeMillis());
		return format.format(date);
	}
}
