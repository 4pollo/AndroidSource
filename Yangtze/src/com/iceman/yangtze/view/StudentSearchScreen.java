
package com.iceman.yangtze.view;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.iceman.yangtze.R;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpRequest;
import com.iceman.yangtze.net.MyHttpResponse;
import com.iceman.yangtze.net.NetConstant;

public class StudentSearchScreen extends WindowActivity {
    private ListView mResultListView;

    private EditText mSearchNameEdit;

    private ArrayList<String[]> hide_params = new ArrayList<String[]>();

    private Button mSubmitBtn, mFuzzySubmitBtn;

    private ArrayList<String[]> mResultItems = new ArrayList<String[]>();

    private ResultListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        overridePendingTransition(R.anim.scale_rotate, R.anim.my_alpha_action);
        mResultListView = (ListView) findViewById(R.id.result_list);
        mSearchNameEdit = (EditText) findViewById(R.id.search_username);
        mSubmitBtn = (Button) findViewById(R.id.search_submit);
        mFuzzySubmitBtn = (Button) findViewById(R.id.fuzzy_search_submit);
        MyHttpRequest req = new MyHttpRequest(NetConstant.TYPE_GET, NetConstant.URL_XSCX, null,
                true);
        req.setPipIndex(NetConstant.XSCX_HOMEPAGE);
        mNetClient.sendRequest(req);
        mSubmitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("txtXm", mSearchNameEdit.getEditableText()
                        .toString().trim()));
                params.add(new BasicNameValuePair("__EVENTTARGET", "btQueryByName"));
                for (String[] arr : hide_params) {
                    // System.out.println("插入hiden参数:" + arr[0] + "\n" +
                    // arr[1]);
                    params.add(new BasicNameValuePair(arr[0], arr[1]));
                }
                MyHttpRequest req = new MyHttpRequest(NetConstant.TYPE_POST, NetConstant.URL_XSCX,
                        params, true);
                req.setPipIndex(NetConstant.XSCX_SEARCH);
                mNetClient.sendRequest(req);
            }
        });
        mFuzzySubmitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("txtXm", mSearchNameEdit.getEditableText()
                        .toString().trim()));
                params.add(new BasicNameValuePair("chkKind", "on"));
                params.add(new BasicNameValuePair("__EVENTTARGET", "btQueryByName"));
                for (String[] arr : hide_params) {
                    // System.out.println("插入hiden参数:" + arr[0] + "\n" +
                    // arr[1]);
                    params.add(new BasicNameValuePair(arr[0], arr[1]));
                }
                MyHttpRequest req = new MyHttpRequest(2, NetConstant.URL_XSCX, params, true);
                req.setPipIndex(NetConstant.XSCX_SEARCH);
                mNetClient.sendRequest(req);
            }
        });
        mListAdapter = new ResultListAdapter();
        mResultListView.setAdapter(mListAdapter);
    }

    private class ResultListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mResultItems.size();
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
                convertView = getLayoutInflater().inflate(R.layout.usersearch_result_list_layout,
                        null);
            }
            TextView id = (TextView) convertView.findViewById(R.id.userid);
            TextView name = (TextView) convertView.findViewById(R.id.username);
            TextView userclass = (TextView) convertView.findViewById(R.id.userclass);
            id.setText(mResultItems.get(position)[0]);
            name.setText(mResultItems.get(position)[1]);
            userclass.setText(mResultItems.get(position)[2]);
            return convertView;
        }

    }

    @Override
    public void handResponse(MyHttpResponse myHttpResponse) {
        Document doc;
        if (myHttpResponse.getPipIndex() == NetConstant.XSCX_HOMEPAGE) {
            doc = myHttpResponse.getData();
            Elements hidden_inputs = doc.select("input[type=hidden]");
            for (Element ele : hidden_inputs) {
                String[] arr = new String[] {
                        ele.attr("name"), ele.attr("value")
                };
                hide_params.add(arr);
                // System.out.println("获得的隐藏参数:" + arr[0] + "\n" + arr[1]);
            }
            hidden_inputs = doc.select("select");
            for (Element ele : hidden_inputs) {
                String[] arr = new String[2];
                arr[0] = ele.attr("name");
                arr[1] = ele.child(0).attr("value");
                hide_params.add(arr);
            }
            System.out.println("获取查询页面信息成功");
            // System.out.println(doc);
        } else if (myHttpResponse.getPipIndex() == NetConstant.XSCX_SEARCH) {
            doc = myHttpResponse.getData();
            System.out.println("学生查询:" + doc.toString());
            Elements results = doc.select("tr[bgcolor=White]");
            if (results != null) {
                mResultItems.clear();
                for (Element ele : results) {
                    Elements childs = ele.children();
                    String[] userinfo = new String[3];
                    userinfo[0] = childs.get(0).text();
                    userinfo[1] = childs.get(1).text();
                    userinfo[2] = childs.get(2).text();
                    mResultItems.add(userinfo);
                }
                mListAdapter.notifyDataSetChanged();
            } else {
            }
            Elements hidden_inputs = doc.select("input[type=hidden]");
            hide_params.clear();
            for (Element ele : hidden_inputs) {
                String[] arr = new String[] {
                        ele.attr("name"), ele.attr("value")
                };
                hide_params.add(arr);
                // System.out.println("获得的隐藏参数:" + arr[0] + "\n" + arr[1]);
            }
            hidden_inputs = doc.select("select");
            for (Element ele : hidden_inputs) {
                String[] arr = new String[2];
                arr[0] = ele.attr("name");
                arr[1] = ele.child(0).attr("value");
                hide_params.add(arr);
            }
        }

    }
}
