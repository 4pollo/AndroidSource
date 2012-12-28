package com.chon.demo.httpoperation;

import com.chon.httpoperation.HttpOperationService;

public class MyService extends HttpOperationService {
	{
		//stop service if there is no more tasks in five minutes
		this.setIdleInMinute(5);
	}
}
