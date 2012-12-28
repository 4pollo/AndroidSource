package com.way.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class ImageLoadUtil {
	private HashMap<String, SoftReference<Drawable>> imageCache;
	private LoadImageListener listener;
	private static boolean isLoad = true;

	public ImageLoadUtil() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public void setUnLock(boolean isLoad) {
		this.isLoad = isLoad;
//		if (isLoad) {
//			Log.i("way", "true");
//			synchronized (this) {
//				notify();
//			}
//		}
	}

	public void setOnLoadImageListener(final String imageUrl,
			final LoadImageListener listener) {
			if (imageCache.containsKey(imageUrl)) {
				SoftReference<Drawable> softReference = imageCache
						.get(imageUrl);
				Drawable drawable = softReference.get();
				if (drawable != null) {
					listener.loadImage(drawable);
					return;
				}
			}
			final Handler handler = new Handler() {
				public void handleMessage(Message message) {
					listener.loadImage((Drawable) message.obj);// 这样返回回去的图片可以直接设置imageView
				}
			};
			// 开一个线程下载图片
			new Thread() {
				@Override
				public void run() {
					try {
//					synchronized (this) {
//						wait();
//					}
//						Log.i("way", "start...");
						Drawable drawable = ImageUtil.geRoundDrawableFromUrl(
								imageUrl, 10);
						imageCache.put(imageUrl, new SoftReference<Drawable>(
								drawable));
						Message message = handler.obtainMessage(0, drawable);
						handler.sendMessage(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
	}

	public interface LoadImageListener {
		public void loadImage(Drawable imageDrawable);
	}
}
