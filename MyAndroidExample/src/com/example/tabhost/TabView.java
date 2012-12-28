package com.example.tabhost;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.R;

public class TabView {

	private static final int INTERVAL_TIME = 50;// 每个tab之间的动画时间间隔ms

	private View tab1;
	private View tab2;
	private View tab3;
	private View tab4;
	
	private Activity context;
	
	private boolean isOpen;

	public TabView(Activity context) {
		this.context = context;
		tab1 = context.findViewById(R.id.tab1);
		tab2 = context.findViewById(R.id.tab2);
		tab3 = context.findViewById(R.id.tab3);
		tab4 = context.findViewById(R.id.tab4);
		setOnClickListener(tab1, 1);
		setOnClickListener(tab2, 2);
		setOnClickListener(tab3, 3);
		setOnClickListener(tab4, 4);
	}

	public void openDock() {//打开dock
		tab(tab1, 0, true);
		tab(tab2, INTERVAL_TIME * 1, true);
		tab(tab3, INTERVAL_TIME * 2, true);
		tab(tab4, INTERVAL_TIME * 3, true);
		isOpen = true;
	}
	
	private void setOnClickListener(View view, final int pos) {
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "view" + pos + " click!", Toast.LENGTH_SHORT).show();//点击tab时显示toast
				closeDock();
			}
		});
	}
	
	private void tab(final View view,int delayTime,final boolean toOpen) {
		view.postDelayed(new Runnable() {

			@Override
			public void run() {
				Animation animation;
				
				if(toOpen) {
					view.setVisibility(View.VISIBLE);//动画开始前,设置view可见
					animation = AnimationUtils.loadAnimation(context, R.anim.show_tab);
					view.startAnimation(animation);
					
				}
				else {
					animation = AnimationUtils.loadAnimation(context, R.anim.close_tab);
					animation.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							view.setVisibility(View.INVISIBLE);//动画结束时,view不可见
							
						}
					});
					view.startAnimation(animation);
					
					
				}
				
			}
		}, delayTime);
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	public void closeDock() {//关闭dock
		tab(tab1, 0, false);
		tab(tab2, INTERVAL_TIME * 1, false);
		tab(tab3, INTERVAL_TIME * 2, false);
		tab(tab4, INTERVAL_TIME * 3, false);
		isOpen = false;
	}
}
