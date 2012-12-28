package com.chon.demo.httpoperation;

import java.io.IOException;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.chon.httpoperation.GetOperation;
import com.chon.httpoperation.HandledResult;
import com.chon.httpoperation.OperationListener;

public class GetDrawableActivity extends Activity {

	private ImageView imageView;
	OperationListener listener = new OperationListener(){

		@Override
		public void onError(long arg0, Bundle arg1, Exception arg2) {
		}

		@Override
		public void onError(long arg0, Bundle arg1, IOException arg2) {
		}

		@Override
		public void onNotOkay(long arg0, Bundle arg1, int arg2, String arg3) {
		}

		@Override
		public void onResult(long arg0, Bundle arg1, HandledResult result) {
			imageView.setImageDrawable((Drawable) result.obj);
			System.out.println(result.obj);
		}
		
	};
	private MyApplication mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mApplication = (MyApplication) this.getApplication();
		initUI();
		loadDrawable();
	}
	
	void initUI(){
		setContentView(R.layout.get_drawable);
		imageView = (ImageView)findViewById(R.id.imageView);
	};
	
	void loadDrawable(){
		String url = "http://images.intomobile.com/wp-content/uploads/2011/01/honeycomb-logo1-e1296259452670.png";
		GetOperation getOperation = new GetOperation(10, url, GetPicHandler.class, listener);
		mApplication.request(getOperation);
	};
	
}
