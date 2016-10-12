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
public class UserAgreementActivity extends BaseActvity {
    private String TAG = "agreement";
    private WebView mServiceWv;

    @Override
    protected int loadLayout() {
        return R.layout.activity_user_agreement;
    }

    @Override
    protected void initTitleBar() {
        initTitle("关于我们");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        showDate();
    }

    private void showDate() {
        WebSettings webSettings = mServiceWv.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        // webSettings.setBuiltInZoomControls(true);
        // 加载需要显示的网页
        mServiceWv.loadUrl(Config.WEB_URL+ActionKey.ABOUT);
        // 设置Web视图
        mServiceWv.setWebChromeClient(new webChromeClient());
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
