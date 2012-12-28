
package com.iceman.yangtze.view;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.iceman.yangtze.Globe;
import com.iceman.yangtze.R;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpRequest;
import com.iceman.yangtze.net.MyHttpResponse;
import com.iceman.yangtze.net.NetConstant;

/**
 * 登陆待完成
 * 
 * @author Administrator
 */
public class LoginScreen extends WindowActivity {
    private Button mLoginButton;

    private CheckBox mSavePasswordBox;

    private EditText mUserName, mPassword;

    private ArrayList<String[]> hide_params = Globe.sHideParams;

    private SharedPreferences mLoginInfoPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mLoginButton = (Button) findViewById(R.id.login_btn);
        mUserName = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mSavePasswordBox = (CheckBox) findViewById(R.id.remember_password);
        mLoginInfoPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
        mUserName.setText(mLoginInfoPreferences.getString("loginname", ""));
        mPassword.setText(mLoginInfoPreferences.getString("loginpassword", ""));
//        MyHttpRequest req = new MyHttpRequest(NetConstant.TYPE_GET, NetConstant.URL_LOGIN, null, false);
//        req.setPipIndex(NetConstant.HOMEPAGE);
//        mNetClient.sendRequest(req);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("txtUid", mUserName.getEditableText().toString().trim()));
                params.add(new BasicNameValuePair("txtPwd", mPassword.getEditableText().toString().trim()));
                params.add(new BasicNameValuePair("selKind", "1"));
                for (String[] arr : hide_params) {
                    params.add(new BasicNameValuePair(arr[0], arr[1]));
                    // System.out.println("放入参数:" + arr[0] + "\n" + arr[1]);
                }
                MyHttpRequest req = new MyHttpRequest(NetConstant.TYPE_POST, NetConstant.URL_LOGIN, params, true);
                req.setPipIndex(NetConstant.LOGIN);
                mNetClient.sendRequest(req);
                LoginScreen.this.showNetLoadingDialog();
                // post.setEntity(new UrlEncodedFormEntity(params, "gb2312"));
            }
        });
    }

    public void handResponse(MyHttpResponse myHttpResponse) {
        System.out.println("处理一个响应");
        Document doc;
        if (myHttpResponse.getPipIndex() == NetConstant.LOGIN) {
            doc = myHttpResponse.getData();
            // System.out.println(doc);
            dismissNetLoadingDialog();
            Element info = doc.getElementById("lbPrompt");
            if (info != null) {
                System.out.println("登陆成功");
                String[] userinfo = info.text().split(" ");
                Globe.sId = userinfo[0];
                Globe.sName = userinfo[1];
                Globe.sClassName = userinfo[2];
                if (mSavePasswordBox.isChecked()) {

                    Editor edit = mLoginInfoPreferences.edit();
                    edit.putString("loginname", mUserName.getEditableText().toString().trim());
                    edit.putString("loginpassword", mPassword.getEditableText().toString().trim());
                    edit.commit();
                }
                Intent it = new Intent(LoginScreen.this, HomePageScreen.class);
                startActivity(it);
                finish();
            } else {
                Elements errorinfo = doc.select("script");
                if (errorinfo.get(0).text().contains("密码错误")) {
                    showTipDialog("账号/密码错误");
                }
            }

        }
    }

}
