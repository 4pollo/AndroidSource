package com.example.gallery.test1;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.R;

public class PageIndicator extends LinearLayout {

	private Context mContext;
	private Drawable mCurrentDrawable;
	private Drawable mNormalDrawable;
	private int mCurrentPage = 0;
	private int mMaxPage = 0 ;
	private ArrayList<ImageView> arrList ;
	public PageIndicator(Context context) {
		super(context);
		this.mContext = context;
		// TODO Auto-generated constructor stub
	}
	
	public PageIndicator(Context paramContext, AttributeSet paramAttributeSet)
	{
	    super(paramContext, paramAttributeSet);
	    this.mContext = paramContext;
	    loadDefaultDrawable();
	}
	
	private void loadDefaultDrawable()
	{
		Resources localResources = this.mContext.getResources();
		this.mNormalDrawable = localResources.getDrawable(R.drawable.tu5503_5);
		this.mCurrentDrawable = localResources.getDrawable(R.drawable.tu5503_7);
	}
	
	public void setMaxPage(int maxNum)
	{
		this.mMaxPage = maxNum;
		init();
	}
	
	private void init(){
		removeAllViews();
		arrList = new ArrayList<ImageView>();
		int i = 0 ;
		if(i>=this.mMaxPage)
		{
			return;
		}
//		ImageView localImageView = new ImageView(this.mContext);
//		localImageView.setPadding(5, 0, 5, 0);
//		if(i==this.mCurrentPage)
//		{
//			localImageView.setImageDrawable(this.mCurrentDrawable);
//		}
		for (i = 0; i < this.mMaxPage; i++) {
			ImageView localImageView = new ImageView(this.mContext);
			localImageView.setPadding(5, 0, 5, 0);
			addView(localImageView);
			if(i==0){
				localImageView.setImageDrawable(this.mCurrentDrawable);
				this.arrList.add(localImageView);
			}else
			{
				localImageView.setImageDrawable(this.mNormalDrawable);
				this.arrList.add(localImageView);
			}
			
		}
		
	}
	
	public void pre(){
		setPage(-1+this.mCurrentPage);
	}
	
	public void next(){
		setPage(1+this.mCurrentPage);
	}
	
	public void setPage(int curPage)
	{
		if(curPage>=this.mMaxPage || curPage<0 || curPage==this.mCurrentPage)
		{
			return;
		}
		this.arrList.get(curPage).setImageDrawable(this.mCurrentDrawable);
		this.arrList.get(this.mCurrentPage).setImageDrawable(this.mNormalDrawable);
		this.mCurrentPage = curPage;
	}
	
	

}