package com.chon.demo.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Bundle;

import com.chon.httpoperation.Handleable;
import com.chon.httpoperation.HandledResult;

public class RssHandler implements Handleable {

	static final String ITEM = "item";
	private int currentstate = -1;
	final int TITLE = 1;
	final int LINK = 2;
	final int DESCRIPTION = 3;
	final int PUBDATE = 4;

	@Override
	public int getContentType() {
		return Handleable.TYPE_STREAM;
	}

	@Override
	public HandledResult handle(String content,Bundle extras) {

		return null;
	}

	@Override
	public HandledResult handle(InputStream content,Bundle extras) {

		XmlPullParserFactory xmlPullParserFactory;
		XmlPullParser xmlPullParser = null;
		try {
			xmlPullParserFactory = XmlPullParserFactory.newInstance();
			xmlPullParserFactory.setNamespaceAware(true);
			xmlPullParser = xmlPullParserFactory.newPullParser();
		} catch (XmlPullParserException e1) {
			e1.printStackTrace();
		}

		RssItem rssItem = null;
		
		ArrayList<RssItem> rssItemList = new ArrayList<RssItem>();
		boolean isItemTAG = false;
		try {
			xmlPullParser.setInput(content, "utf-8");

			int eventType = xmlPullParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_DOCUMENT) {
					System.out.println("start Document...");
				} else if (eventType == XmlPullParser.END_DOCUMENT) {
					System.out.println("end Document...");
				} else if (eventType == XmlPullParser.START_TAG) {
					if (xmlPullParser.getName().equals("item")) {
						rssItem = new RssItem();
						isItemTAG = true;
					}
					if (xmlPullParser.getName().equals("title")) {
						currentstate = TITLE;
					}
					if (xmlPullParser.getName().equals("link")) {
						currentstate = LINK;
					}
					if (xmlPullParser.getName().equals("description")) {
						currentstate = DESCRIPTION;
					}
					if (xmlPullParser.getName().equals("pubDate")) {
						currentstate = PUBDATE;
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if (xmlPullParser.getName().equals("item")) {
						rssItemList.add(rssItem);
					}

				} else if (eventType == XmlPullParser.TEXT) {
					if (isItemTAG) {
						switch (currentstate) {
						case TITLE:
							System.out.println(xmlPullParser.getText());
							rssItem.setTitle(clearSpecialChar(xmlPullParser
									.getText()));
							currentstate = -1;
							break;
						case LINK:
							rssItem.setLink(clearSpecialChar(xmlPullParser
									.getText()));
							currentstate = -1;
							break;
						case DESCRIPTION:
							rssItem.setDescription(clearSpecialChar(xmlPullParser
									.getText()));
							currentstate = -1;
							break;
						case PUBDATE:
							rssItem.setPubData(clearSpecialChar(xmlPullParser
									.getText()));
							currentstate = -1;
							break;
						default:
							break;
						}
					}
				}

				eventType = xmlPullParser.next();

			}
			for (RssItem item : rssItemList) {
				System.out.println(item);
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HandledResult(null,rssItemList,null);

	}

	private String clearSpecialChar(String s) {
		Pattern pattern = Pattern.compile("\\s|\\r|\\n|\\t");
		Matcher matcher = pattern.matcher(s);
		return matcher.replaceAll("").trim();
	}

}