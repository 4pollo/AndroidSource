
package com.iceman.yangtze.view;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iceman.yangtze.R;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpRequest;
import com.iceman.yangtze.net.MyHttpResponse;
import com.iceman.yangtze.net.NetConstant;

public class StudentInfoEditScreen extends WindowActivity implements OnClickListener {
    private TextView mInfoTextView;

    private RadioGroup mSexGroup;

    private RadioButton mSexRadioBoy;

    private Button mSfzSubmit, mSexSubmit;

    private EditText mSfzEditText;

    private String mNumber, mName, mClassName, mSex, mSfzNumber;

    private ArrayList<String[]> hide_params = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info_edit_layout);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        mInfoTextView = (TextView) findViewById(R.id.info);
        mSexGroup = (RadioGroup) findViewById(R.id.sex_group);
        mSexRadioBoy = (RadioButton) findViewById(R.id.boy);
        mSfzSubmit = (Button) findViewById(R.id.sfz_submit);
        mSexSubmit = (Button) findViewById(R.id.sex_submit);
        mSfzEditText = (EditText) findViewById(R.id.sfz_number);
        MyHttpRequest req = new MyHttpRequest(NetConstant.TYPE_GET, NetConstant.URL_INFO, null,
                true);
        req.setPipIndex(NetConstant.INFO_HOMEPAGE);
        mNetClient.sendRequest(req);
        mSfzSubmit.setOnClickListener(this);
        mSexSubmit.setOnClickListener(this);
    }

    @Override
    public void handResponse(MyHttpResponse myHttpResponse) {
        if (myHttpResponse.getPipIndex() == NetConstant.INFO_HOMEPAGE) {
            Document doc = myHttpResponse.getData();
            // System.out.println("查询个人信息:" + doc.toString());
            Element ele = doc.getElementById("lbXh");
            if (ele == null) {
                StudentInfoEditScreen.this.showDialog(NetConstant.MSG_NET_ERROR);
            }
            mNumber = ele.text();
            ele = doc.getElementById("lbXm");
            mName = ele.text();
            ele = doc.getElementById("lbBj");
            mClassName = ele.text();
            ele = doc.getElementById("lbXb");
            mSex = ele.text();
            if (mSex.equals("男")) {
                mSexGroup.check(R.id.boy);
            } else if (mSex.equals("女")) {
                mSexGroup.check(R.id.girl);
            }
            mInfoTextView.setText(mNumber + " " + mName + " " + mClassName + " " + mSex);

            ele = doc.getElementById("txtSfz");
            mSfzNumber = ele.attr("value");
            mSfzEditText.setText(mSfzNumber);

            Elements hidden_inputs = doc.select("input[type=hidden]");
            for (Element hidden_eles : hidden_inputs) {
                String[] arr = new String[] {
                        hidden_eles.attr("name"), hidden_eles.attr("value")
                };
                hide_params.add(arr);
                // System.out.println("获得的隐藏参数:" + arr[0] + "\n" + arr[1]);
            }
            System.out.println("获取个人信息成功");
            Toast.makeText(StudentInfoEditScreen.this, "由于教务处网站问题,修改性别后请退回至主界面再进来,才能看到变化...",
                    Toast.LENGTH_SHORT).show();
        } else if (myHttpResponse.getPipIndex() == NetConstant.INFO_EDIT) {
            Document doc = myHttpResponse.getData();
            // System.out.println("个人信息修改后:" + doc.toString());
            Element ele = doc.getElementById("lbXh");
            if (ele == null) {
                StudentInfoEditScreen.this.showDialog(NetConstant.MSG_NET_ERROR);
            }
            mNumber = ele.text();
            ele = doc.getElementById("lbXm");
            mName = ele.text();
            ele = doc.getElementById("lbBj");
            mClassName = ele.text();
            ele = doc.getElementById("lbXb");
            mSex = ele.text();
            if (mSex.equals("男")) {
                mSexGroup.check(R.id.boy);
            } else if (mSex.equals("女")) {
                mSexGroup.check(R.id.girl);
            }

            ele = doc.getElementById("txtSfz");
            mSfzNumber = ele.attr("value");
            mInfoTextView.setText(mNumber + " " + mName + " " + mClassName + " " + mSex + "\n"
                    + mSfzNumber);
            mSfzEditText.setText(mSfzNumber);

            Elements hidden_inputs = doc.select("input[type=hidden]");
            for (Element hidden_eles : hidden_inputs) {
                String[] arr = new String[] {
                        hidden_eles.attr("name"), hidden_eles.attr("value")
                };
                hide_params.add(arr);
                // System.out.println("获得的隐藏参数:" + arr[0] + "\n" + arr[1]);
            }
            System.out.println("获取修改后的个人信息成功");
        }

    }

    @Override
    public void onClick(View v) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        MyHttpRequest req;
        switch (v.getId()) {
            case R.id.sex_submit:
                params.add(new BasicNameValuePair("txtSfz", mSfzNumber));
                params.add(new BasicNameValuePair("rdXb", mSexRadioBoy.isChecked() ? "男" : "女"));
                params.add(new BasicNameValuePair("btXb", "修改性别"));
                for (String[] arr : hide_params) {
                    // System.out.println("插入hiden参数:" + arr[0] + "\n" +
                    // arr[1]);
                    params.add(new BasicNameValuePair(arr[0], arr[1]));
                }
                req = new MyHttpRequest(NetConstant.TYPE_POST, NetConstant.URL_INFO, params, true);
                req.setPipIndex(NetConstant.INFO_EDIT);

                mNetClient.sendRequest(req);
                break;
            case R.id.sfz_submit:
                String regEx = "\\d{17}(\\d|x|X)"; // 表示a或F
                Pattern pat = Pattern.compile(regEx);
                Matcher mat = pat.matcher(mSfzEditText.getEditableText().toString().trim());
                if (!mat.find()) {
                    Toast.makeText(StudentInfoEditScreen.this, "身份证号码不规范!", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    params.add(new BasicNameValuePair("txtSfz", mSfzEditText.getEditableText()
                            .toString().trim()));
                    params.add(new BasicNameValuePair("btSfz", "修改身份证号"));
                    params.add(new BasicNameValuePair("rdXb", mSex));
                    for (String[] arr : hide_params) {
                        // System.out.println("插入hiden参数:" + arr[0] + "\n" +
                        // arr[1]);
                        params.add(new BasicNameValuePair(arr[0], arr[1]));
                    }
                    req = new MyHttpRequest(NetConstant.TYPE_POST, NetConstant.URL_INFO, params,
                            true);
                    req.setPipIndex(NetConstant.INFO_EDIT);

                    mNetClient.sendRequest(req);
                }
                break;

            default:
                break;
        }
    }

}
