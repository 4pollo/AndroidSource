
package com.iceman.yangtze.view;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.Bundle;
import android.widget.TextView;

import com.iceman.yangtze.Globe;
import com.iceman.yangtze.R;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpRequest;
import com.iceman.yangtze.net.MyHttpResponse;
import com.iceman.yangtze.net.NetConstant;

public class CheckUpdateScreen extends WindowActivity {
    private TextView mVersionView, mUrlView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        setContentView(R.layout.check_update_layout);
        mVersionView = (TextView) findViewById(R.id.version_info);
        mUrlView = (TextView) findViewById(R.id.url_info);
        MyHttpRequest req = new MyHttpRequest(NetConstant.TYPE_GET, NetConstant.URL_UPDATE, null,
                true);
        req.setPipIndex(NetConstant.UPDATE);
        mNetClient.sendRequest(req);
    }

    @Override
    public void handResponse(MyHttpResponse myHttpResponse) {
        if (myHttpResponse.getPipIndex() == NetConstant.UPDATE) {
            Document doc = myHttpResponse.getData();
//            System.out.println(doc.toString());
            Element ele = doc.getElementById("content");
            String[] updateStrs = ele.text().split("@");
            String versionStr = updateStrs[0];
            String urlStr = updateStrs[1];
            String news = updateStrs[2];
            int version_int = Integer.parseInt(versionStr);
            if (version_int > Globe.sVersionInt) {
                mVersionView.setText("有新的版本了噢..." + "\n" + "更新内容:" + "\n" + news);
                mUrlView.setText(urlStr);
            } else {
                mVersionView.setText("目前使用的是最新版." + "\n");
            }
        }

    }

}
