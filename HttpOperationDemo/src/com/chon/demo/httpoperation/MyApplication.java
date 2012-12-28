package com.chon.demo.httpoperation;

import com.chon.httpoperation.HttpOperationApplication;

public class MyApplication extends HttpOperationApplication {
	{
		this.setOperationService(MyService.class);
	}
}
