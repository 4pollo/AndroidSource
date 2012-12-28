package com.example.gallery.test1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.R;
import com.example.gallery.GalleryTest;

public class Guide extends Activity {

	private GuideGallery gImage;
	private int index = -1;
	private boolean isLastImg = false;

	private GestureDetector mGestureDetector;
	private PageIndicator mPageIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		gImage = (GuideGallery) findViewById(R.id.gImage);
		mPageIndicator = (PageIndicator) findViewById(R.id.guide_pageindicator);
		mPageIndicator.setMaxPage(2);

		gImage.setAdapter(new ImageAdapter(this));
		mGestureDetector = new GestureDetector(new MyGesture());

		gImage.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				// index = gImage.getCount()-1;
				mPageIndicator.setPage(position);
				index = position;
				if (index == gImage.getCount() - 1) {
					isLastImg = true;
					index++;
				} else {
					isLastImg = false;
					index--;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
		// scroll.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	public class MyGesture extends SimpleOnGestureListener {

		int minDistanceX = 20;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			if (e1.getX() - e2.getX() > minDistanceX) {
				if (index == gImage.getCount() && isLastImg) {
					Intent intent = new Intent();
					intent.setClass(Guide.this, GalleryTest.class);
					startActivity(intent);
					Guide.this.finish();

				}
				Toast.makeText(Guide.this, "向左手势", Toast.LENGTH_SHORT).show();
			} else if (e2.getX() - e1.getX() > minDistanceX) {
				Toast.makeText(Guide.this, "向右手势", Toast.LENGTH_SHORT).show();
			}
			// return super.onFling(e1, e2, velocityX, velocityY);
			return false;
		}
	}

	public class ImageAdapter extends BaseAdapter {
		private Context myContext;
		/** 定义图片 */
		private int[] myImages = { R.drawable.tu5503_4, R.drawable.tu5503_5

		};

		/** 存储Context */
		public ImageAdapter(Context c) {
			this.myContext = c;

		}

		/** 获得图片数量 */
		public int getCount() {
			// TODO Auto-generated method stub
			return this.myImages.length;
		}

		/** 获得目前图像数组ID */
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

		/** 取得显示图像View,传入数组ID值读取数组图像 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			/** 实例化ImageView对象 */
			ImageView i = new ImageView(this.myContext);
			/** 设置图片 */
			i.setImageResource(this.myImages[position]);
			/** 设置View的大小 */
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			/** 设置ImageView对象宽度和高度 */
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			return i;
		}

		/** 距离中央距离位移梁 利用getScale返回View大小 */
		public float getScale(boolean flg, int offset) {
			return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
		}
	}

}