
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.iceman.yangtze.Globe;
import com.iceman.yangtze.R;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpRequest;
import com.iceman.yangtze.net.MyHttpResponse;
import com.iceman.yangtze.net.NetClient;
import com.iceman.yangtze.net.NetConstant;

public class ScoreSearchScreen extends WindowActivity implements OnClickListener {
    private NetClient mNetClient = Globe.sNetClient;

    private ArrayList<String[]> hide_params = new ArrayList<String[]>();

    private String[] mYearStrs;

    private String[] mTermStrs;

    private int mCurSelectYearIndex, mCurSelectTermIndex;

    private Spinner mYearSpinner, mTermSpinner;

    private ArrayAdapter<String> mYesrSpinnerAdapter;

    private ArrayAdapter<String> mTermSpinnerAdapter;

    private Button mScoreSearchBtn, mAllScoreBtn, mExSearchBtnOne, mExSearchBtnTwo;

    private ListView mResultListView;

    private ArrayList<String[]> mScoreListResults = new ArrayList<String[]>();

    private ScoreListAdapter mScoreListAdapter;

    private ArrayList<String[]> mInputStrs = new ArrayList<String[]>();

    private TextView mJdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_search_layout);
        overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
        System.out.println("cookie:" + Globe.sCookieString);
        System.out.println("进入成绩查询页面");
        mJdTextView = (TextView) findViewById(R.id.jd_text);
        mYearSpinner = (Spinner) findViewById(R.id.year_spinner);
        mTermSpinner = (Spinner) findViewById(R.id.term_spinner);
        mScoreSearchBtn = (Button) findViewById(R.id.search_btn);
        mAllScoreBtn = (Button) findViewById(R.id.all_score_btn);
        mExSearchBtnOne = (Button) findViewById(R.id.ex_search_btn1);
        mExSearchBtnTwo = (Button) findViewById(R.id.ex_search_btn2);
        mScoreSearchBtn.setOnClickListener(this);
        mAllScoreBtn.setOnClickListener(this);
        mExSearchBtnOne.setOnClickListener(this);
        mExSearchBtnTwo.setOnClickListener(this);
        mResultListView = (ListView) findViewById(R.id.score_result_list);
        mScoreListAdapter = new ScoreListAdapter();
        mResultListView.setAdapter(mScoreListAdapter);
        MyHttpRequest req = new MyHttpRequest(NetConstant.TYPE_GET, NetConstant.URL_CJCX, null,
                true);
        req.setPipIndex(NetConstant.CJCX_HOMEPAGE);
        mNetClient.sendRequest(req);
    }

    @Override
    public void handResponse(MyHttpResponse myHttpResponse) {
        if (myHttpResponse.getPipIndex() == NetConstant.CJCX_HOMEPAGE) {
            Document doc = myHttpResponse.getData();
            // System.out.println("查询首页:" + doc.toString());
            Element ele = doc.getElementById("selYear");
            Elements eles = ele.getAllElements();
            mYearStrs = new String[eles.size() - 1];
            for (int i = 1; i < eles.size(); i++) {
                mYearStrs[i - 1] = eles.get(i).text();
                if (eles.get(i).hasAttr("selected")) {
                    mCurSelectYearIndex = i;
                }
            }
            ele = doc.getElementById("selTerm");
            eles = ele.getAllElements();
            mTermStrs = new String[eles.size() - 1];
            for (int i = 1; i < eles.size(); i++) {
                mTermStrs[i - 1] = eles.get(i).text();
                if (eles.get(i).hasAttr("selected")) {
                    mCurSelectTermIndex = i;
                }
            }
            mYesrSpinnerAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mYearStrs);
            mYesrSpinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mYearSpinner.setAdapter(mYesrSpinnerAdapter);
            mYearSpinner.setSelection(mCurSelectYearIndex);
            mTermSpinnerAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mTermStrs);
            mTermSpinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTermSpinner.setAdapter(mTermSpinnerAdapter);
            mTermSpinner.setSelection(mCurSelectTermIndex);
            Elements hidden_inputs = doc.select("input[type=hidden]");
            for (Element hidden_eles : hidden_inputs) {
                String[] arr = new String[] {
                        hidden_eles.attr("name"), hidden_eles.attr("value")
                };
                hide_params.add(arr);
                // System.out.println("获得的隐藏参数:" + arr[0] + "\n" + arr[1]);
            }
            Elements submit_inputs = doc.select("input[type=button],input[type=submit]");
            for (Element submit_ele : submit_inputs) {
                String[] arr = new String[] {
                        submit_ele.attr("name"), submit_ele.attr("value")
                };
                mInputStrs.add(arr);
            }
            System.out.println("获取查询页面信息成功");
        } else if (myHttpResponse.getPipIndex() == NetConstant.CJCX_SEARCH) {
            mScoreListResults.clear();
            Document doc = myHttpResponse.getData();
            Elements eles = doc.select("tr[align=left]");
            for (Element ele : eles) {
                Elements items = ele.getElementsByAttribute("face");
                String[] str = new String[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    str[i] = items.get(i).text();
                }
                mScoreListResults.add(str);
            }
            mScoreListAdapter.notifyDataSetChanged();

            Element ele = doc.getElementById("jd");
            mJdTextView.setText(ele.text());

            hide_params.clear();
            Elements hidden_inputs = doc.select("input[type=hidden]");
            for (Element hidden_eles : hidden_inputs) {
                String[] arr = new String[] {
                        hidden_eles.attr("name"), hidden_eles.attr("value")
                };
                hide_params.add(arr);
                // System.out.println("获得的隐藏参数:" + arr[0] + "\n" + arr[1]);
            }
            // System.out.println(doc.toString());
        }
    }

    @Override
    public void onClick(View v) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        MyHttpRequest req;
        switch (v.getId()) {
            case R.id.search_btn:
                params.add(new BasicNameValuePair("selTerm", ""
                        + (mTermSpinner.getSelectedItemPosition() + 1)));
                params.add(new BasicNameValuePair("selYear", mYearStrs[mYearSpinner
                        .getSelectedItemPosition()]));
                params.add(new BasicNameValuePair("__EVENTTARGET", mInputStrs.get(0)[0]));
                for (String[] arr : hide_params) {
                    System.out.println("插入hiden参数:" + arr[0] + "\n" + arr[1]);
                    params.add(new BasicNameValuePair(arr[0], arr[1]));
                }
                req = new MyHttpRequest(NetConstant.TYPE_POST, NetConstant.URL_CJCX, params, true);
                req.setPipIndex(NetConstant.CJCX_SEARCH);
                mNetClient.sendRequest(req);
                break;
            case R.id.all_score_btn:
                params.add(new BasicNameValuePair("selTerm", ""
                        + (mTermSpinner.getSelectedItemPosition() + 1)));
                params.add(new BasicNameValuePair("selYear", mYearStrs[mYearSpinner
                        .getSelectedItemPosition()]));
                params.add(new BasicNameValuePair("__EVENTTARGET", mInputStrs.get(1)[0]));
                for (String[] arr : hide_params) {
                    System.out.println("插入hiden参数:" + arr[0] + "\n" + arr[1]);
                    params.add(new BasicNameValuePair(arr[0], arr[1]));
                }
                req = new MyHttpRequest(NetConstant.TYPE_POST, NetConstant.URL_CJCX, params, true);
                req.setPipIndex(NetConstant.CJCX_SEARCH);
                mNetClient.sendRequest(req);
                break;
            case R.id.ex_search_btn1:
                params.add(new BasicNameValuePair("selTerm", ""
                        + (mTermSpinner.getSelectedItemPosition() + 1)));
                params.add(new BasicNameValuePair("selYear", mYearStrs[mYearSpinner
                        .getSelectedItemPosition()]));
                params.add(new BasicNameValuePair(mInputStrs.get(2)[0], mInputStrs.get(2)[1]));
                for (String[] arr : hide_params) {
                    System.out.println("插入hiden参数:" + arr[0] + "\n" + arr[1]);
                    params.add(new BasicNameValuePair(arr[0], arr[1]));
                }
                req = new MyHttpRequest(NetConstant.TYPE_POST, NetConstant.URL_CJCX, params, true);
                req.setPipIndex(NetConstant.CJCX_SEARCH);
                mNetClient.sendRequest(req);
                break;
            case R.id.ex_search_btn2:
                params.add(new BasicNameValuePair("selTerm", ""
                        + (mTermSpinner.getSelectedItemPosition() + 1)));
                params.add(new BasicNameValuePair("selYear", mYearStrs[mYearSpinner
                        .getSelectedItemPosition()]));
                params.add(new BasicNameValuePair(mInputStrs.get(3)[0], mInputStrs.get(3)[1]));
                for (String[] arr : hide_params) {
                    System.out.println("插入hiden参数:" + arr[0] + "\n" + arr[1]);
                    params.add(new BasicNameValuePair(arr[0], arr[1]));
                }
                req = new MyHttpRequest(NetConstant.TYPE_POST, NetConstant.URL_CJCX, params, true);
                req.setPipIndex(NetConstant.CJCX_SEARCH);
                mNetClient.sendRequest(req);
                break;

            default:
                break;
        }
    }

    private class ScoreListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mScoreListResults.size();
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
                convertView = getLayoutInflater().inflate(R.layout.score_list_item_layout, null);
            }
            TextView classname = (TextView) convertView.findViewById(R.id.class_name);
            classname.setText(mScoreListResults.get(position)[0]);
            TextView score = (TextView) convertView.findViewById(R.id.score);
            score.setText(mScoreListResults.get(position)[1]);
            TextView credit = (TextView) convertView.findViewById(R.id.credit);
            credit.setText(mScoreListResults.get(position)[2]);
            TextView year = (TextView) convertView.findViewById(R.id.year);
            year.setText(mScoreListResults.get(position)[3]);
            TextView term = (TextView) convertView.findViewById(R.id.term);
            term.setText(mScoreListResults.get(position)[4]);
            TextView class_type = (TextView) convertView.findViewById(R.id.class_type);
            class_type.setText(mScoreListResults.get(position)[5]);
            return convertView;
        }

    }
}
