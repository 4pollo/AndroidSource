
package com.iceman.yangtze.view;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import com.iceman.yangtze.R;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpResponse;

public class AboutScreen extends WindowActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
        TextView text = new TextView(this);
        text.setBackgroundResource(R.drawable.about_bg);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        text.setText("\n\n" + "" + "长江大学教务处android客户端V2.2" + "\n\n" + "送给学弟学妹们的话:" + "\n"
                + "外面的世界很精彩,so,如果不想以后空羡慕的话,就在大学好好学习吧." + "\n\n"
                + "作者简介:2010年毕业学长一枚,在上海从事android应用开发,开发这个小程序既是方便大家也是锻炼自己.联系QQ:78952190");
        setContentView(text);
    }

    @Override
    public void handResponse(MyHttpResponse myHttpResponse) {

    }

}
