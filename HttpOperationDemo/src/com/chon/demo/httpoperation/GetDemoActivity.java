package com.chon.demo.httpoperation;

import java.io.IOException;

import com.chon.httpoperation.GetOperation;
import com.chon.httpoperation.HandledResult;
import com.chon.httpoperation.OperationListener;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GetDemoActivity extends Activity {

	private EditText urlEdit;
	private TextView htmlText;
	private Button submitButton;
	
	OperationListener listener = new OperationListener(){

		@Override
		public void onError(long arg0, Bundle arg1, Exception e) {
			htmlText.setText("E:"  + e);
		}

		@Override
		public void onError(long arg0, Bundle arg1, IOException e) {
			htmlText.setText("IOE:"  + e);
		}

		@Override
		public void onNotOkay(long arg0, Bundle arg1, int code, String content) {
			htmlText.setText("code:" + code + "content:" + content);
		}

		@Override
		public void onResult(long arg0, Bundle arg1, HandledResult result) {
			htmlText.setText(result.extras.getString("html"));
		}
		
	};
	private MyApplication mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mApplication = (MyApplication)this.getApplication();
		initUI();
	}
	
	private void initUI(){
		this.setContentView(R.layout.get_demo);
		urlEdit = (EditText)findViewById(R.id.urlEdit);
		htmlText = (TextView)findViewById(R.id.htmlText);
		htmlText.setMovementMethod(ScrollingMovementMethod.getInstance());
		submitButton = (Button)findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				GetOperation getOperation = new GetOperation(10, urlEdit.getText().toString(),
						DummyHtmlHandler.class, listener);
				mApplication.request(getOperation);
			}
		});
	}
}
