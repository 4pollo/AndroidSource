package com.boyle.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class BaseActivity extends Activity {
	private View titleView;
	private TextView tv_title;
	private Button btn_left, btn_right;
	private LinearLayout ly_content;
	// 内容区域的布局
	private View contentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.global_template);
		
		titleView = findViewById(R.id.globalTop);
		tv_title = (TextView) titleView.findViewById(R.id.txtBarTitle);
		btn_left = (Button) titleView.findViewById(R.id.btnLeft);
		btn_right = (Button) titleView.findViewById(R.id.btnRight);

		ly_content = (LinearLayout) findViewById(R.id.MainWrap);
	}

	public BaseActivity() {
	}

	/***
	 * 设置内容区域
	 * 
	 * @param resId
	 *            资源文件ID
	 */
	public void setContentLayout(int resId) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(resId, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		contentView.setLayoutParams(layoutParams);
		contentView.setBackgroundDrawable(null);
		if (null != ly_content) {
			ly_content.addView(contentView);
		}
	}

	/***
	 * 设置内容区域
	 * 
	 * @param view
	 *            View对象
	 */
	public void setContentLayout(View view) {
		if (null != ly_content) {
			ly_content.addView(view);
		}
	}

	/**
	 * 得到内容的View
	 * 
	 * @return
	 */
	public View getLyContentView() {
		return contentView;
	}

	/**
	 * 得到左边的按钮
	 * 
	 * @return
	 */
	public Button getbtn_left() {
		return btn_left;
	}

	/**
	 * 得到右边的按钮
	 * 
	 * @return
	 */
	public Button getbtn_right() {
		return btn_right;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		if (null != tv_title) {
			tv_title.setText(title);
		}
	}

	/**
	 * 设置标题
	 * 
	 * @param resId
	 */
	public void setTitle(int resId) {
		tv_title.setText(getString(resId));
	}

	/**
	 * 设置左边按钮的图片资源
	 * 
	 * @param resId
	 */
	public void setbtn_leftRes(int resId) {
		if (null != btn_left) {
			btn_left.setBackgroundResource(resId);
		}
	}

	/**
	 * 设置左边按钮的图片资源
	 * 
	 * @param bm
	 */
	public void setbtn_leftRes(Drawable drawable) {
		if (null != btn_left) {
			btn_left.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param resId
	 */
	public void setbtn_rightRes(int resId) {
		if (null != btn_right) {
			btn_right.setBackgroundResource(resId);
		}
	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param drawable
	 */
	public void setbtn_rightRes(Drawable drawable) {
		if (null != btn_right) {
			btn_right.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * 隐藏上方的标题栏
	 */
	public void hideTitleView() {
		if (null != titleView) {
			titleView.setVisibility(View.GONE);
		}
	}

	/**
	 * 隐藏左边的按钮
	 */
	public void hidebtn_left() {
		if (null != btn_left) {
			btn_left.setVisibility(View.GONE);
		}
	}

	/***
	 * 隐藏右边的按钮
	 */
	public void hidebtn_right() {
		if (null != btn_right) {
			btn_right.setVisibility(View.GONE);
		}
	}
}
