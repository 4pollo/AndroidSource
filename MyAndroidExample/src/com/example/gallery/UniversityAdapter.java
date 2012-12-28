package com.example.gallery;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class UniversityAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<University> universityList;

	public UniversityAdapter(Context context,
			ArrayList<University> universityList) {
		this.context = context;
		this.universityList = universityList;
	}

	// 这里返回一个较大整数是为了实现Gallery的循环播放
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private int select = 0;

	public void notifyDataSetChanged(int albumId) {
		select = albumId;
		super.notifyDataSetChanged();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView = new ImageView(context);// 初始化ImageView，用来显示各项
		if (select == position) {// 当此项是选中项时，对起进行特殊处理
			University pro = universityList.get(position
					% universityList.size());// 要实现循环播放，这里需要对数据大小进行求余
			imageView.setImageBitmap(BitmapUtil.createReflectedImage(BitmapUtil
					.createTxtImage(pro.getName(), 28)));// 这里通过自定义的BitmapUtil类中的两个方法，实现了倒影效果
		} else {
			University pro = universityList.get(position
					% universityList.size());
			imageView.setImageBitmap(BitmapUtil.createTxtImage(pro.getName(),
					22));// 这设置文字大小为22。实现了未选中项的变小效果
		}
		return imageView;
	}
}