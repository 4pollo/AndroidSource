
package com.iceman.yangtze.view;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.iceman.yangtze.Globe;
import com.iceman.yangtze.R;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpRequest;
import com.iceman.yangtze.net.MyHttpResponse;
import com.iceman.yangtze.net.NetConstant;

public class InitScreen extends WindowActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_screen_layout);
        getAPN();
    }

    private void getAPN() {
        try {
            // 通过context得到ConnectivityManager连接管理
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            // 通过ConnectivityManager得到NetworkInfo网络信息
            NetworkInfo info = manager.getActiveNetworkInfo();
            // 获取NetworkInfo中的apn信息
            if (info == null) {
                System.out.println("没有可用的网络");
                showDialog(NetConstant.MSG_NONE_NET);
            } else {
                MyHttpRequest req = new MyHttpRequest(NetConstant.TYPE_GET, NetConstant.URL_LOGIN, null, false);
                req.setPipIndex(NetConstant.HOMEPAGE);
                mNetClient.sendRequest(req);
                System.out.println("开始连接首页");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handResponse(MyHttpResponse myHttpResponse) {
        Document doc;
        if (myHttpResponse.getPipIndex() == NetConstant.HOMEPAGE) {
            doc = myHttpResponse.getData();
            Elements hidden_inputs = doc.select("input[type=hidden]");
            for (Element ele : hidden_inputs) {
                String[] arr = new String[] {
                        ele.attr("name"), ele.attr("value")
                };
                Globe.sHideParams.add(arr);
            }
            hidden_inputs = doc.select("input[type=submit]");
            Globe.sHideParams.add(new String[] {
                    hidden_inputs.get(0).attr("name"), hidden_inputs.get(0).attr("value")
            });
            System.out.println("获取首页信息成功");
            Intent it = new Intent(InitScreen.this, LoginScreen.class);
            startActivity(it);
            finish();
            // System.out.println(doc);
        }
    }

}
