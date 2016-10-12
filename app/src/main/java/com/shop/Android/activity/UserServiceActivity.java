package com.shop.Android.activity;

import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.king.Internet.user_method.CallServer;
import com.shop.Android.Config;
import com.shop.Android.base.BaseActvity;
import com.shop.Net.ActionKey;
import com.shop.R;

/**
 * Created by admin on 2016/9/23.
 */
public class UserServiceActivity extends BaseActvity {
    private String TAG = "user";
    private WebView mWebWv;
    @Override
    protected int loadLayout() {
        return R.layout.activity_user_service;
    }

    @Override
    protected void initTitleBar() {
        initTitle("用户服务");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
      F();
        showData();
    }

    private void showData() {
        WebSettings webSettings = mWebWv.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        // webSettings.setBuiltInZoomControls(true);
        // 加载需要显示的网页
        mWebWv.loadUrl(Config.WEB_URL+ActionKey.USER_SERVICE);
        // 设置Web视图
        mWebWv.setWebChromeClient(new webChromeClient());
    }

    private class webChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
            }
        }
    }

    @Override
    protected void onClickSet(int i) {

    }
}
