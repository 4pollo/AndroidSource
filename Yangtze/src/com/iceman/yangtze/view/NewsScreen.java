
package com.iceman.yangtze.view;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.iceman.yangtze.R;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpRequest;
import com.iceman.yangtze.net.MyHttpResponse;
import com.iceman.yangtze.net.NetConstant;

public class NewsScreen extends WindowActivity {
    private ListView mListView;

    private ArrayList<String[]> mLeftListItems = new ArrayList<String[]>();

    private ArrayList<String[]> mRightListItems = new ArrayList<String[]>();

    public static String MAIN_URL = "http://jwc.yangtzeu.edu.cn:8080/";

    private ListViewAdapter mAdapter;

    private RadioGroup mRadioGroup;

    private int listtype = 0;// 默认为通知公告

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.homepage_listview);
        mAdapter = new ListViewAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(NewsScreen.this, NewsInfoScreen.class);
                if (listtype == 0) {

                    it.putExtra("name", mLeftListItems.get(position)[1]);
                    it.putExtra("url", mLeftListItems.get(position)[0]);
                } else {
                    it.putExtra("name", mRightListItems.get(position)[1]);
                    it.putExtra("url", mRightListItems.get(position)[0]);
                }
                startActivity(it);
            }
        });
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tongzhigonggao:
                        listtype = 0;
                        break;
                    case R.id.jiaoxuedongtai:
                        listtype = 1;
                        break;
                    default:
                        break;
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        MyHttpRequest req = new MyHttpRequest(NetConstant.TYPE_GET, NetConstant.URL_HOMEPAGE, null,
                true);
        req.setPipIndex(NetConstant.HOMEPAGE);
        mNetClient.sendRequest(req);
    }

    private class ListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mLeftListItems.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.homepage_listview_layout, null);
            }
            if (listtype == 0) {
                TextView name = (TextView) convertView.findViewById(R.id.name);
                name.setText(mLeftListItems.get(position)[1]);
                TextView time = (TextView) convertView.findViewById(R.id.time);
                time.setText(mLeftListItems.get(position)[2]);
            } else {
                TextView name = (TextView) convertView.findViewById(R.id.name);
                name.setText(mRightListItems.get(position)[1]);
                TextView time = (TextView) convertView.findViewById(R.id.time);
                time.setText(mRightListItems.get(position)[2]);
            }

            return convertView;
        }

    }

    @Override
    public void handResponse(MyHttpResponse myHttpResponse) {
        if (myHttpResponse.getPipIndex() == NetConstant.HOMEPAGE) {
            Document doc = myHttpResponse.getData();
            Elements tables = doc.select("table[id=DgTz]");
            Elements items = tables.select("a[name=Hyperlink1]");
            for (Element link : items) {
                String[] arr = new String[3];
                arr[0] = MAIN_URL + link.attr("href");
                arr[1] = link.text();
                arr[2] = link.parent().nextElementSibling().text();
                mLeftListItems.add(arr);
            }

            tables = doc.select("table[id=DgDt]");
            items = tables.select("a[name=Hyperlink1]");
            for (Element link : items) {
                String[] arr = new String[3];
                arr[0] = MAIN_URL + link.attr("href");
                arr[1] = link.text();
                arr[2] = link.parent().nextElementSibling().text();
                mRightListItems.add(arr);
            }
            mAdapter.notifyDataSetChanged();
        }

    }

}
