package com.example.service;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.R;

public class TestService extends Activity {
	Button btnStartMyService, btnStopMyService, btnBindMyService,
			btnUnbindMyService, btnExit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicemain);
		btnStartMyService = (Button) this.findViewById(R.id.btnStartMyService);
		btnStartMyService.setOnClickListener(new ClickEvent());

		btnStopMyService = (Button) this.findViewById(R.id.btnStopMyService);
		btnStopMyService.setOnClickListener(new ClickEvent());

		btnBindMyService = (Button) this.findViewById(R.id.btnBindMyService);
		btnBindMyService.setOnClickListener(new ClickEvent());

		btnUnbindMyService = (Button) this
				.findViewById(R.id.btnUnbindMyService);
		btnUnbindMyService.setOnClickListener(new ClickEvent());

		btnExit = (Button) this.findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new ClickEvent());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("Activity", "onDestroy");
	}

	private ServiceConnection _connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
		}
	};

	class ClickEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(TestService.this, MyService.class);
			if (v == btnStartMyService) {
				TestService.this.startService(intent);
			} else if (v == btnStopMyService) {
				TestService.this.stopService(intent);
			} else if (v == btnBindMyService) {
				TestService.this.bindService(intent, _connection,
						Service.BIND_AUTO_CREATE);
			} else if (v == btnUnbindMyService) {
				if (MyService.ServiceState == "onBind")// Service绑定了之后才能解绑
					TestService.this.unbindService(_connection);
			} else if (v == btnExit) {
				TestService.this.finish();
			}

		}

	}
}