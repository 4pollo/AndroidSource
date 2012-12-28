package com.way.util.adapter;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.way.util.ImageLoadUtil;
import com.way.util.ImageLoadUtil.LoadImageListener;
import com.way.util.ImageUtil;
import com.way.util.TextUtil;
import com.way.util.TimeUtil;
import com.way.util.view.MyListView;
import com.way.weibo.activity.R;

/**
 * 
 * @author way
 * 
 */
public class MyListViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private LinkedList<JSONObject> list;
	private Context context;
	private HashMap<String, SoftReference<Drawable>> imageCache;
	private MyListView myListView;
	private ImageLoadUtil loader;

	public MyListViewAdapter(Context context, LinkedList<JSONObject> list,
			MyListView myListView) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
		this.myListView = myListView;
		imageCache = new HashMap<String, SoftReference<Drawable>>();
		loader = new ImageLoadUtil();
		loader.setUnLock(true);
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.home_listview_item, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			resetViewHolder(holder);
		}
		holder.head = (ImageView) convertView.findViewById(R.id.home_item_head);
		holder.name = (TextView) convertView.findViewById(R.id.home_item_name);
		holder.vip = (ImageView) convertView.findViewById(R.id.home_item_vip);
		holder.time = (TextView) convertView.findViewById(R.id.home_item_time);
		holder.content = (TextView) convertView
				.findViewById(R.id.home_item_content);
		holder.img = (ImageView) convertView.findViewById(R.id.home_item_pic);
		holder.address = (TextView) convertView
				.findViewById(R.id.home_item_address);
		holder.from = (TextView) convertView.findViewById(R.id.home_item_from);
		holder.count = (TextView) convertView
				.findViewById(R.id.home_item_count);

		holder.view = convertView.findViewById(R.id.home_item_source_layout);
		holder.picLayout = (RelativeLayout) convertView// 图片view
				.findViewById(R.id.home_item_pic_layout);
		holder.addressLayout = (RelativeLayout) convertView// 地址view
				.findViewById(R.id.home_item_address_layout);
		JSONObject data = (JSONObject) list.get(position);
		if (data != null) {
			try {
				// convertView.setTag(data.get("id"));
				final String headUrl = data.getString("head") + "/100";
				holder.head.setTag(headUrl);
				loader.setOnLoadImageListener(headUrl, new LoadImageListener() {

					public void loadImage(Drawable imageDrawable) {
						// TODO Auto-generated method stub
						ImageView imgHead = (ImageView) myListView
								.findViewWithTag(headUrl);
						if (imgHead != null)
							holder.head.setImageDrawable(imageDrawable);
					}
				});
				holder.name.setText(data.getString("nick"));// 昵称
				if (data.getInt("isvip") != 1)
					holder.vip.setVisibility(View.GONE);// 如果不所会员，隐藏会员图标
				holder.time.setText(TimeUtil.converTime(Long.parseLong(data
						.getString("timestamp"))));// 时间
				String origtext = data.getString("origtext");
				SpannableString spannableSource = new SpannableString(origtext);
				// 匹配"#话题#"类型
				spannableSource = TextUtil.decorateRefersInStr(spannableSource,
						TextUtil.getStartAndEndIndex(

						origtext, Pattern.compile("#.*#")),
						context.getResources());
				// 匹配超链接
				spannableSource = TextUtil.decorateURLInStr(
						spannableSource,
						TextUtil.getStartAndEndIndex(origtext,
								Pattern.compile("http.* ")),
						context.getResources());
				// 匹配表情
				spannableSource = TextUtil.decorateFaceInStr(
						spannableSource,
						TextUtil.getStartAndEndIndex(origtext,
								Pattern.compile("\\/[\u4e00-\u9fa5]{1,3}")),
						context.getResources());
				holder.content.setText(spannableSource);// 微博内容

				// 转载等内容
				if (!data.getString("source").equals("null")) {
					JSONObject source = data.getJSONObject("source");
					// Log.i("way", source.toString());
					holder.view.setVisibility(View.VISIBLE);
					final ImageView head = (ImageView) holder.view
							.findViewById(R.id.include_head);
					TextView name = (TextView) holder.view
							.findViewById(R.id.include_other_name);
					name.setText(source.getString("nick"));
					ImageView vip = (ImageView) holder.view
							.findViewById(R.id.include_vip);
					vip.setVisibility(View.INVISIBLE);
					TextView text = (TextView) holder.view
							.findViewById(R.id.include_origtext);
					final RelativeLayout pic_layout = (RelativeLayout) holder.view
							.findViewById(R.id.include_pic_layout);
					if (source.getInt("isvip") == 1) {
						vip.setVisibility(View.VISIBLE);
					}
					SpannableString spannableSourceInclude = new SpannableString(
							source.getString("origtext"));
					// 匹配"#话题#"类型
					spannableSourceInclude = TextUtil.decorateRefersInStr(
							spannableSource, TextUtil.getStartAndEndIndex(

							origtext, Pattern.compile("#.*#")),
							context.getResources());
					// 匹配超链接
					spannableSourceInclude = TextUtil.decorateURLInStr(
							spannableSource,
							TextUtil.getStartAndEndIndex(origtext,
									Pattern.compile("http.* ")),
							context.getResources());
					// 匹配表情
					spannableSourceInclude = TextUtil
							.decorateFaceInStr(
									spannableSource,
									TextUtil.getStartAndEndIndex(
											origtext,
											Pattern.compile("\\/[\u4e00-\u9fa5]{1,3}")),
									context.getResources());
					text.setText(spannableSourceInclude);
					final String includeHeadUrl = source.getString("head")
							+ "/100";
					head.setTag(includeHeadUrl);
					loader.setOnLoadImageListener(includeHeadUrl,
							new LoadImageListener() {

								public void loadImage(Drawable imageDrawable) {
									// TODO Auto-generated method stub
									ImageView includeHead = (ImageView) myListView
											.findViewWithTag(includeHeadUrl);
									if (includeHead != null)
										includeHead
												.setImageDrawable(imageDrawable);
								}
							});
					JSONArray imageArrayInclude = source.optJSONArray("image");
					if (imageArrayInclude != null
							&& imageArrayInclude.length() > 0) {// 如果是有图片的微博
						pic_layout.setVisibility(View.VISIBLE);
						final String imageUrlInclude = imageArrayInclude
								.optString(0) + "/460";
						loader.setOnLoadImageListener(imageUrlInclude,
								new LoadImageListener() {

									public void loadImage(Drawable imageDrawable) {
										// TODO Auto-generated method stub
										ImageView pic = (ImageView) pic_layout
												.findViewById(R.id.include_pic);
										pic.setImageDrawable(imageDrawable);
									}
								});
					}
				}
				

				JSONArray imageArray = data.optJSONArray("image");
				if (imageArray != null && imageArray.length() > 0) {// 如果是有图片的微博
					holder.picLayout.setVisibility(View.VISIBLE);
					final String imageUrl = imageArray.optString(0) + "/460";
					holder.img.setTag(imageUrl);
					loader.setOnLoadImageListener(imageUrl,
							new LoadImageListener() {

								public void loadImage(Drawable imageDrawable) {
									// TODO Auto-generated method stub
									ImageView iView = (ImageView) myListView
											.findViewWithTag(imageUrl);
									if (iView != null)
										iView.setImageDrawable(imageDrawable);
								}
							});
				}
				if (data.getString("geo").length() > 0) {// 如果有地址
					holder.addressLayout.setVisibility(View.VISIBLE);
					holder.address.setText(data.getString("geo"));
				} else if (!data.getString("location").equals("未知")) {
					holder.addressLayout.setVisibility(View.VISIBLE);
					holder.address.setText(data.getString("location"));
				}
				holder.from.setText("来自 " + data.getString("from"));// 来自
				holder.count.setText((data.getInt("mcount") + data
						.getInt("count")) + "");// 点评次数
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return convertView;
	}

	protected void resetViewHolder(ViewHolder holder) {
		holder.head.setImageDrawable(null);
		holder.name.setText(null);
		holder.vip.setImageDrawable(null);
		holder.time.setText(null);
		holder.content.setText(null);
		holder.img.setImageDrawable(null);
		holder.address.setText(null);
		holder.from.setText(null);
		holder.count.setText(null);
		holder.view.setVisibility(View.GONE);
		holder.picLayout.setVisibility(View.GONE);
		holder.addressLayout.setVisibility(View.GONE);
	}

	static class ViewHolder {
		public ImageView head;// 头像
		public TextView name;// 名字
		public ImageView vip;// 是否为vip
		public TextView time;// 时间
		public TextView content;// 微博内容
		public ImageView img;// 有图片的微博
		public TextView address;// 定位地址
		public TextView from;// 来自
		public TextView count;// 点评次数
		public View view;
		public RelativeLayout picLayout;
		public RelativeLayout addressLayout;
	}

	/**
	 * 异步读取网络图片,暂时没用到，纠结加载时的性能优化
	 * 
	 * @author way
	 */
	class AsyncLoadImage extends AsyncTask<Object, Object, Void> {
		@Override
		protected Void doInBackground(Object... params) {
			try {
				String url = (String) params[0];
				Drawable drawable = ImageUtil.geRoundDrawableFromUrl(url, 10);
				imageCache.put(url, new SoftReference<Drawable>(drawable));// 缓存起来
				publishProgress(new Object[] { url, drawable });
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onProgressUpdate(Object... progress) {
			ImageView imageView = (ImageView) myListView
					.findViewWithTag(progress[0]);
			if (imageView != null)
				imageView.setImageDrawable((Drawable) progress[1]);
		}
	}

}
