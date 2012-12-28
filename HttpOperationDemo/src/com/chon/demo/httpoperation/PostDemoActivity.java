package com.chon.demo.httpoperation;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chon.httpoperation.HandledResult;
import com.chon.httpoperation.OperationListener;
import com.chon.httpoperation.PostOperation;

public class PostDemoActivity extends Activity {

	
	private Button submitButton;
	
	OperationListener listener = new OperationListener(){

		@Override
		public void onError(long arg0, Bundle arg1, Exception e) {
			e.printStackTrace();
			responseText.setText("E:"  + e);
		}

		@Override
		public void onError(long arg0, Bundle arg1, IOException e) {
			responseText.setText("IOE:"  + e);
		}

		@Override
		public void onNotOkay(long arg0, Bundle arg1, int code, String content) {
			responseText.setText("code:" + code + "content:" + content);
		}

		@Override
		public void onResult(long arg0, Bundle arg1, HandledResult result) {
			System.out.println(Thread.currentThread().getId());
			responseText.setText(result.extras.getString("html"));
		}
		
	};
	private MyApplication mApplication;
	private TextView responseText;
	private EditText usernameEdit;
	private EditText passwordEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mApplication = (MyApplication)this.getApplication();
		initUI();
	}
	
	private void initUI(){
		this.setContentView(R.layout.post_demo);
		usernameEdit = (EditText)findViewById(R.id.usernameEdit);
		passwordEdit = (EditText)findViewById(R.id.passwordEdit);
		responseText = (TextView)findViewById(R.id.responseText);
		responseText.setMovementMethod(ScrollingMovementMethod.getInstance());
		submitButton = (Button)findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String url = "http://www.winu.cn/post_datas.php";
				PostOperation postOperation = new PostOperation(10, url,
						DummyHtmlHandler.class, listener);
				postOperation.addBasicNameValuePairs("id", usernameEdit.getText().toString());
				postOperation.addBasicNameValuePairs("stringdata", passwordEdit.getText().toString());
				mApplication.request(postOperation);
			}
		});
	}
}
