package com.boyle.util.adapter;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyle.model.MusicModel;
import com.boyle.musicplayer.R;
import com.boyle.utils.ImageLoader;

public class LazyAdapter extends BaseAdapter {

	private Vector<MusicModel> mModels = new Vector<MusicModel>();
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader; // 用来下载图片的类，后面有介绍

	public LazyAdapter(Activity activity) {
		super();
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public void addMusic(String id, String title, String artist,
			String duration, String thumburl) {
		MusicModel model = new MusicModel();
		model.setId(id);
		model.setTitle(title);
		model.setArtist(artist);
		model.setDuration(duration);
		model.setThumburl(thumburl);
		mModels.add(model);
	}

	public void clean() {
		mModels.clear();
	}
	
	public int getCount() {
		return mModels.size();
	}

	public Object getItem(int position) {
		if (position >= getCount()) {
			return null;
		}
		return mModels.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_row, null);

		TextView title = (TextView) vi.findViewById(R.id.title); // 标题
		TextView artist = (TextView) vi.findViewById(R.id.artist); // 歌手名
		TextView duration = (TextView) vi.findViewById(R.id.duration); // 时长
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // 缩略图

		MusicModel model = mModels.get(position);
		String strTitle = model.getTitle();
		// 如果字符串长度大于20，则后面的显示省略号
		strTitle = (strTitle.length() > 20) ? (strTitle.substring(0, 20) + "...")
				: strTitle;
		title.setText(strTitle);
		artist.setText(model.getId() + "/" + model.getArtist());
		duration.setText(model.getDuration());
		imageLoader.DisplayImage(model.getThumburl(), thumb_image);

		return vi;
	}
}