package com.lisen.android.weijingxuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lisen.android.weijingxuan.R;

/**
 * Created by Administrator on 2016/7/31.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String url = intent.getStringExtra("detail_url");
        Log.d("url", "url= " + url);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_detail);

        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.back_detail);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }

        WebView webView = (WebView) findViewById(R.id.wb_activity_detail);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.loadUrl(url);
    }
}
