
package com.iceman.yangtze.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.Toast;

import com.iceman.yangtze.R;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpResponse;

public class ClassScoreSearchScreen extends WindowActivity {

    private WebView mWebView;

    protected SeekBar progressHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        Toast.makeText(ClassScoreSearchScreen.this, "淡定...这只是个网页", Toast.LENGTH_SHORT).show();
        FrameLayout rootViewLayout = new FrameLayout(this);
        progressHorizontal = new SeekBar(this);
        progressHorizontal.setBackgroundDrawable(this.getResources().getDrawable(
                R.drawable.seekbar_bg));
        progressHorizontal.setThumb(this.getResources().getDrawable(R.drawable.thumb));
        progressHorizontal.setProgressDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressHorizontal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mWebView = new WebView(this);
        rootViewLayout.addView(mWebView);
        rootViewLayout.addView(progressHorizontal);
        LayoutParams pa = (LayoutParams) progressHorizontal.getLayoutParams();
        pa.gravity = Gravity.CENTER;
        pa.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        pa.width = FrameLayout.LayoutParams.FILL_PARENT;
        progressHorizontal.setLayoutParams(pa);
        setContentView(rootViewLayout);

        overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://jwc.yangtzeu.edu.cn:8080/bjcj.aspx");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100 && progressHorizontal.getVisibility() != View.VISIBLE) {
                    progressHorizontal.setVisibility(View.VISIBLE);
                }
                progressHorizontal.setProgress(newProgress);
                progressHorizontal.postInvalidate();
                if (newProgress == 100) {
                    progressHorizontal.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void handResponse(MyHttpResponse myHttpResponse) {

    }

}
