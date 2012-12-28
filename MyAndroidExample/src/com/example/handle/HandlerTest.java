package com.example.handle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.R;

public class HandlerTest extends Activity {
	TextView text;
	String str = "我是好人，别杀我~";
	private Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				text.setText(str);
				break;
			case 0:
				break;
			default:
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handlemain);
		text = (TextView) findViewById(R.id.myTextView1);
	

		Thread thred = new Thread() {
			@Override
			public void run() {
				
				for(int i=0;i<10000;i++){
					try {
						sleep(3000);
						str=str+i;
						Message msg= new Message();
						msg.what=1;
						handle.sendMessage(msg);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
				// TODO Auto-generated method stub
				super.run();
			}
		};
		thred.start();

	}
}