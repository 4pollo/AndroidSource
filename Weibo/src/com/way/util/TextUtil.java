package com.way.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;

import com.way.weibo.activity.R;

public class TextUtil {

	public static Map<String, Integer> faceNameToDrawableId = new HashMap<String, Integer>();
	public static Map<Integer, String> drawableIdToFaceName = new HashMap<Integer, String>();

	static {
		faceNameToDrawableId.put("微笑", R.drawable.h001);
		faceNameToDrawableId.put("撇嘴", R.drawable.h002);
		faceNameToDrawableId.put("色", R.drawable.h003);
		faceNameToDrawableId.put("发呆", R.drawable.h004);
		faceNameToDrawableId.put("得意", R.drawable.h005);
		faceNameToDrawableId.put("流泪", R.drawable.h006);
		faceNameToDrawableId.put("害羞", R.drawable.h007);
		faceNameToDrawableId.put("闭嘴", R.drawable.h008);
		faceNameToDrawableId.put("睡", R.drawable.h009);
		faceNameToDrawableId.put("大哭", R.drawable.h010);
		faceNameToDrawableId.put("尴尬", R.drawable.h011);
		faceNameToDrawableId.put("发怒", R.drawable.h012);
		faceNameToDrawableId.put("调皮", R.drawable.h013);
		faceNameToDrawableId.put("呲牙", R.drawable.h014);
		faceNameToDrawableId.put("惊讶", R.drawable.h015);
		faceNameToDrawableId.put("难过", R.drawable.h016);
		faceNameToDrawableId.put("酷", R.drawable.h017);
		faceNameToDrawableId.put("冷汗", R.drawable.h018);
		faceNameToDrawableId.put("抓狂", R.drawable.h019);
		faceNameToDrawableId.put("吐", R.drawable.h020);
		faceNameToDrawableId.put("偷笑", R.drawable.h021);
		faceNameToDrawableId.put("可爱", R.drawable.h022);
		faceNameToDrawableId.put("白眼", R.drawable.h023);
		faceNameToDrawableId.put("傲慢", R.drawable.h024);
		faceNameToDrawableId.put("饥饿", R.drawable.h025);
		faceNameToDrawableId.put("困", R.drawable.h026);
		faceNameToDrawableId.put("惊恐", R.drawable.h027);
		faceNameToDrawableId.put("流汗", R.drawable.h028);
		faceNameToDrawableId.put("憨笑", R.drawable.h029);
		faceNameToDrawableId.put("大兵", R.drawable.h030);
		faceNameToDrawableId.put("奋斗", R.drawable.h031);
		faceNameToDrawableId.put("咒骂", R.drawable.h032);
		faceNameToDrawableId.put("疑问", R.drawable.h033);
		faceNameToDrawableId.put("嘘", R.drawable.h034);
		faceNameToDrawableId.put("晕", R.drawable.h035);
		faceNameToDrawableId.put("折磨", R.drawable.h036);
		faceNameToDrawableId.put("衰", R.drawable.h037);
		faceNameToDrawableId.put("骷髅", R.drawable.h038);
		faceNameToDrawableId.put("敲打", R.drawable.h039);
		faceNameToDrawableId.put("再见", R.drawable.h040);
		faceNameToDrawableId.put("擦汗", R.drawable.h041);
		faceNameToDrawableId.put("抠鼻", R.drawable.h042);
		faceNameToDrawableId.put("鼓掌", R.drawable.h043);
		faceNameToDrawableId.put("糗大了", R.drawable.h044);
		faceNameToDrawableId.put("坏笑", R.drawable.h045);
		faceNameToDrawableId.put("左哼哼", R.drawable.h046);
		faceNameToDrawableId.put("右哼哼", R.drawable.h047);
		faceNameToDrawableId.put("哈欠", R.drawable.h048);
		faceNameToDrawableId.put("鄙视", R.drawable.h049);
		faceNameToDrawableId.put("委屈", R.drawable.h050);
		faceNameToDrawableId.put("快哭了", R.drawable.h051);
		faceNameToDrawableId.put("阴险", R.drawable.h052);
		faceNameToDrawableId.put("亲亲", R.drawable.h053);
		faceNameToDrawableId.put("吓", R.drawable.h054);
		faceNameToDrawableId.put("可怜", R.drawable.h055);
		faceNameToDrawableId.put("菜刀", R.drawable.h056);
		faceNameToDrawableId.put("西瓜", R.drawable.h057);
		faceNameToDrawableId.put("啤酒", R.drawable.h058);
		faceNameToDrawableId.put("篮球", R.drawable.h059);
		faceNameToDrawableId.put("乒乓", R.drawable.h060);
		faceNameToDrawableId.put("咖啡", R.drawable.h061);
		faceNameToDrawableId.put("饭", R.drawable.h062);
		faceNameToDrawableId.put("猪头", R.drawable.h063);
		faceNameToDrawableId.put("玫瑰", R.drawable.h064);
		faceNameToDrawableId.put("凋谢", R.drawable.h065);
		faceNameToDrawableId.put("示爱", R.drawable.h066);
		faceNameToDrawableId.put("爱心", R.drawable.h067);
		faceNameToDrawableId.put("心碎", R.drawable.h068);
		faceNameToDrawableId.put("蛋糕", R.drawable.h069);
		faceNameToDrawableId.put("闪电", R.drawable.h070);
		faceNameToDrawableId.put("炸弹", R.drawable.h071);
		faceNameToDrawableId.put("刀", R.drawable.h072);
		faceNameToDrawableId.put("足球", R.drawable.h073);
		faceNameToDrawableId.put("瓢虫", R.drawable.h074);
		faceNameToDrawableId.put("便便", R.drawable.h075);
		faceNameToDrawableId.put("月亮", R.drawable.h076);
		faceNameToDrawableId.put("太阳", R.drawable.h077);
		faceNameToDrawableId.put("礼物", R.drawable.h078);
		faceNameToDrawableId.put("拥抱", R.drawable.h079);
		faceNameToDrawableId.put("强", R.drawable.h080);
		faceNameToDrawableId.put("弱", R.drawable.h081);
		faceNameToDrawableId.put("握手", R.drawable.h082);
		faceNameToDrawableId.put("胜利", R.drawable.h083);
		faceNameToDrawableId.put("握拳", R.drawable.h084);
		faceNameToDrawableId.put("勾引", R.drawable.h085);
		faceNameToDrawableId.put("拳头", R.drawable.h086);
		faceNameToDrawableId.put("差劲", R.drawable.h087);
		faceNameToDrawableId.put("爱你", R.drawable.h088);
		faceNameToDrawableId.put("NO", R.drawable.h089);
		faceNameToDrawableId.put("OK", R.drawable.h090);
		faceNameToDrawableId.put("爱情", R.drawable.h091);
		faceNameToDrawableId.put("飞吻", R.drawable.h092);
		faceNameToDrawableId.put("跳跳", R.drawable.h093);
		faceNameToDrawableId.put("发抖", R.drawable.h094);
		faceNameToDrawableId.put("怄火", R.drawable.h095);
		faceNameToDrawableId.put("转圈", R.drawable.h096);
		faceNameToDrawableId.put("磕头", R.drawable.h097);
		faceNameToDrawableId.put("回头", R.drawable.h098);
		faceNameToDrawableId.put("跳绳", R.drawable.h099);
		faceNameToDrawableId.put("挥手", R.drawable.h100);

		faceNameToDrawableId.put("激动", R.drawable.h101);
		faceNameToDrawableId.put("街舞", R.drawable.h102);
		faceNameToDrawableId.put("献吻", R.drawable.h103);
		faceNameToDrawableId.put("左太极", R.drawable.h104);
		faceNameToDrawableId.put("右太极", R.drawable.h105);
	}

	static {
		drawableIdToFaceName.put(R.drawable.h001, "微笑");
		drawableIdToFaceName.put(R.drawable.h002, "撇嘴");
		drawableIdToFaceName.put(R.drawable.h003, "色");
		drawableIdToFaceName.put(R.drawable.h004, "发呆");
		drawableIdToFaceName.put(R.drawable.h005, "得意");
		drawableIdToFaceName.put(R.drawable.h006, "流泪");
		drawableIdToFaceName.put(R.drawable.h007, "害羞");
		drawableIdToFaceName.put(R.drawable.h008, "闭嘴");
		drawableIdToFaceName.put(R.drawable.h009, "睡");
		drawableIdToFaceName.put(R.drawable.h010, "大哭");
		drawableIdToFaceName.put(R.drawable.h011, "尴尬");
		drawableIdToFaceName.put(R.drawable.h012, "发怒");
		drawableIdToFaceName.put(R.drawable.h013, "调皮");
		drawableIdToFaceName.put(R.drawable.h014, "呲牙");
		drawableIdToFaceName.put(R.drawable.h015, "惊讶");
		drawableIdToFaceName.put(R.drawable.h016, "难过");
		drawableIdToFaceName.put(R.drawable.h017, "酷");
		drawableIdToFaceName.put(R.drawable.h018, "冷汗");
		drawableIdToFaceName.put(R.drawable.h019, "抓狂");
		drawableIdToFaceName.put(R.drawable.h020, "吐");
		drawableIdToFaceName.put(R.drawable.h021, "偷笑");
		drawableIdToFaceName.put(R.drawable.h022, "可爱");
		drawableIdToFaceName.put(R.drawable.h023, "白眼");
		drawableIdToFaceName.put(R.drawable.h024, "傲慢");
		drawableIdToFaceName.put(R.drawable.h025, "饥饿");
		drawableIdToFaceName.put(R.drawable.h026, "困");
		drawableIdToFaceName.put(R.drawable.h027, "惊恐");
		drawableIdToFaceName.put(R.drawable.h028, "流汗");
		drawableIdToFaceName.put(R.drawable.h029, "憨笑");
		drawableIdToFaceName.put(R.drawable.h030, "大兵");
		drawableIdToFaceName.put(R.drawable.h031, "奋斗");
		drawableIdToFaceName.put(R.drawable.h032, "咒骂");
		drawableIdToFaceName.put(R.drawable.h033, "疑问");
		drawableIdToFaceName.put(R.drawable.h034, "嘘");
		drawableIdToFaceName.put(R.drawable.h035, "晕");
		drawableIdToFaceName.put(R.drawable.h036, "折磨");
		drawableIdToFaceName.put(R.drawable.h037, "衰");
		drawableIdToFaceName.put(R.drawable.h038, "骷髅");
		drawableIdToFaceName.put(R.drawable.h039, "敲打");
		drawableIdToFaceName.put(R.drawable.h040, "再见");
		drawableIdToFaceName.put(R.drawable.h041, "擦汗");
		drawableIdToFaceName.put(R.drawable.h042, "抠鼻");
		drawableIdToFaceName.put(R.drawable.h043, "鼓掌");
		drawableIdToFaceName.put(R.drawable.h044, "糗大了");
		drawableIdToFaceName.put(R.drawable.h045, "坏笑");
		drawableIdToFaceName.put(R.drawable.h046, "左哼哼");
		drawableIdToFaceName.put(R.drawable.h047, "右哼哼");
		drawableIdToFaceName.put(R.drawable.h048, "哈欠");
		drawableIdToFaceName.put(R.drawable.h049, "鄙视");
		drawableIdToFaceName.put(R.drawable.h050, "委屈");
		drawableIdToFaceName.put(R.drawable.h051, "快哭了");
		drawableIdToFaceName.put(R.drawable.h052, "阴险");
		drawableIdToFaceName.put(R.drawable.h053, "亲亲");
		drawableIdToFaceName.put(R.drawable.h054, "吓");
		drawableIdToFaceName.put(R.drawable.h055, "可怜");
		drawableIdToFaceName.put(R.drawable.h056, "菜刀");
		drawableIdToFaceName.put(R.drawable.h057, "西瓜");
		drawableIdToFaceName.put(R.drawable.h058, "啤酒");
		drawableIdToFaceName.put(R.drawable.h059, "篮球");
		drawableIdToFaceName.put(R.drawable.h060, "乒乓");
		drawableIdToFaceName.put(R.drawable.h061, "咖啡");
		drawableIdToFaceName.put(R.drawable.h062, "饭");
		drawableIdToFaceName.put(R.drawable.h063, "猪头");
		drawableIdToFaceName.put(R.drawable.h064, "玫瑰");
		drawableIdToFaceName.put(R.drawable.h065, "凋谢");
		drawableIdToFaceName.put(R.drawable.h066, "示爱");
		drawableIdToFaceName.put(R.drawable.h067, "爱心");
		drawableIdToFaceName.put(R.drawable.h068, "心碎");
		drawableIdToFaceName.put(R.drawable.h069, "蛋糕");
		drawableIdToFaceName.put(R.drawable.h070, "闪电");
		drawableIdToFaceName.put(R.drawable.h071, "炸弹");
		drawableIdToFaceName.put(R.drawable.h072, "刀");
		drawableIdToFaceName.put(R.drawable.h073, "足球");
		drawableIdToFaceName.put(R.drawable.h074, "瓢虫");
		drawableIdToFaceName.put(R.drawable.h075, "便便");
		drawableIdToFaceName.put(R.drawable.h076, "月亮");
		drawableIdToFaceName.put(R.drawable.h077, "太阳");
		drawableIdToFaceName.put(R.drawable.h078, "礼物");
		drawableIdToFaceName.put(R.drawable.h079, "拥抱");
		drawableIdToFaceName.put(R.drawable.h080, "强");
		drawableIdToFaceName.put(R.drawable.h081, "弱");
		drawableIdToFaceName.put(R.drawable.h082, "握手");
		drawableIdToFaceName.put(R.drawable.h083, "胜利");
		drawableIdToFaceName.put(R.drawable.h084, "握拳");
		drawableIdToFaceName.put(R.drawable.h085, "勾引");
		drawableIdToFaceName.put(R.drawable.h086, "拳头");
		drawableIdToFaceName.put(R.drawable.h087, "差劲");
		drawableIdToFaceName.put(R.drawable.h088, "爱你");
		drawableIdToFaceName.put(R.drawable.h089, "NO");
		drawableIdToFaceName.put(R.drawable.h090, "OK");
		drawableIdToFaceName.put(R.drawable.h091, "爱情");
		drawableIdToFaceName.put(R.drawable.h092, "飞吻");
		drawableIdToFaceName.put(R.drawable.h093, "跳跳");
		drawableIdToFaceName.put(R.drawable.h094, "发抖");
		drawableIdToFaceName.put(R.drawable.h095, "怄火");
		drawableIdToFaceName.put(R.drawable.h096, "转圈");
		drawableIdToFaceName.put(R.drawable.h097, "磕头");
		drawableIdToFaceName.put(R.drawable.h098, "回头");
		drawableIdToFaceName.put(R.drawable.h099, "跳绳");
		drawableIdToFaceName.put(R.drawable.h100, "挥手");

		drawableIdToFaceName.put(R.drawable.h101, "激动");
		drawableIdToFaceName.put(R.drawable.h102, "街舞");
		drawableIdToFaceName.put(R.drawable.h103, "献吻");
		drawableIdToFaceName.put(R.drawable.h104, "左太极");
		drawableIdToFaceName.put(R.drawable.h105, "右太极");
	}

	/**
	 * 
	 * @param sourceStr
	 * @param pattern
	 * @return
	 */
	public static List<Map<String, Object>> getStartAndEndIndex(
			String sourceStr, Pattern pattern) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Matcher matcher = pattern.matcher(sourceStr);
		boolean isFind = matcher.find();
		while (isFind) {
			Map<String, Object> map = new HashMap<String, Object>();
			String faceName = matcher.group().substring(1,
					matcher.group().length());// 表情名称或超链接
			// Log.i("face", faceName);
			map.put("startIndex", matcher.start());
			map.put("endIndex", matcher.end());
			map.put("content", faceName);
			list.add(map);
			isFind = matcher.find((Integer) map.get("endIndex") - 1);
		}
		return list;
	}

	/**
	 * 从字符串中获取表情
	 * 
	 * @param spannable
	 * @param list
	 * @param resources
	 * @return
	 */
	public static SpannableString decorateFaceInStr(SpannableString spannable,
			List<Map<String, Object>> list, Resources resources) {
		int size = list.size();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < size; i++) {
				Map<String, Object> map = list.get(i);
				if (map.get("content") != null
						&& faceNameToDrawableId.get(map.get("content")) != null) {
					Drawable drawable = resources
							.getDrawable(faceNameToDrawableId.get(map
									.get("content")));
					drawable.setBounds(10, 10,
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight());
					ImageSpan span = new ImageSpan(drawable,
							ImageSpan.ALIGN_BASELINE);
					spannable.setSpan(span, (Integer) map.get("startIndex"),
							(Integer) map.get("endIndex"),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
		}
		return spannable;
	}

	/**
	 * 高亮显示
	 * 
	 * @param spannable
	 * @param list
	 * @param resources
	 * @return
	 */
	public static SpannableString decorateHighLightInStr(
			SpannableString spannable, List<Map<String, Object>> list,
			Resources resources) {
		int size = list.size();
		CharacterStyle foregroundColorSpan = new ForegroundColorSpan(
				resources.getColor(R.color.list_view_item_name_color));// 蓝色前景色
		if (list != null && list.size() > 0) {
			for (int i = 0; i < size; i++) {
				Map<String, Object> map = list.get(i);
				// 设置高亮颜色
				spannable.setSpan(foregroundColorSpan,
						(Integer) map.get("startIndex"),
						(Integer) map.get("endIndex"),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return spannable;
	}

	/**
	 * 设置超链接
	 * 
	 * @param spannable
	 * @param list
	 * @param resources
	 * @return
	 */
	public static SpannableString decorateURLInStr(SpannableString spannable,
			List<Map<String, Object>> list, Resources resources) {
		int size = list.size();
		CharacterStyle foregroundColorSpan = new ForegroundColorSpan(
				resources.getColor(R.color.list_view_item_name_color));// 蓝色前景色
		if (list != null && list.size() > 0) {
			for (int i = 0; i < size; i++) {
				Map<String, Object> map = list.get(i);
				// 设置超链接
				spannable.setSpan(new URLSpan((String) map.get("content")),
						(Integer) map.get("startIndex"),
						(Integer) map.get("endIndex"),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				// 设置高亮
				spannable.setSpan(foregroundColorSpan,
						(Integer) map.get("startIndex"),
						(Integer) map.get("endIndex"),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return spannable;
	}

	/**
	 * #话题# 类型的高亮
	 * 
	 * @param spannable
	 * @param list
	 * @param resources
	 * @return
	 */
	public static SpannableString decorateRefersInStr(
			SpannableString spannable, List<Map<String, Object>> list,
			Resources resources) {
		int size = list.size();
		CharacterStyle foregroundColorSpan = new ForegroundColorSpan(
				resources.getColor(R.color.list_view_item_name_color));
		if (list != null && list.size() > 0) {
			for (int i = 0; i < size; i++) {
				Map<String, Object> map = list.get(i);
				spannable.setSpan(foregroundColorSpan,
						(Integer) map.get("startIndex"),
						(Integer) map.get("endIndex"),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return spannable;
	}
}
