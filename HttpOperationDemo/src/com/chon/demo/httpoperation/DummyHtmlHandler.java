package com.chon.demo.httpoperation;

import java.io.InputStream;

import android.os.Bundle;

import com.chon.httpoperation.Handleable;
import com.chon.httpoperation.HandledResult;

public class DummyHtmlHandler implements Handleable {

	@Override
	public int getContentType() {
		return Handleable.TYPE_STRING;
	}

	@Override
	public HandledResult handle(String content, Bundle bundle) {
		Bundle extras = new Bundle();
		extras.putString("html", content);
		return new HandledResult(extras, null, null);
	}

	@Override
	public HandledResult handle(InputStream arg0, Bundle arg1) {
		return null;
	}

}
