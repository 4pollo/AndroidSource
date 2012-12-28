package com.chon.demo.httpoperation;

import java.io.InputStream;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.chon.httpoperation.Handleable;
import com.chon.httpoperation.HandledResult;

public class GetPicHandler implements Handleable{

	@Override
	public int getContentType() {
		return Handleable.TYPE_STREAM;
	}

	@Override
	public HandledResult handle(String arg0, Bundle arg1) {
		return null;
	}

	@Override
	public HandledResult handle(InputStream is, Bundle extras) {
		Drawable drawable = Drawable.createFromStream(is, "tag");
		return new HandledResult(null,null,drawable);
	}

}
