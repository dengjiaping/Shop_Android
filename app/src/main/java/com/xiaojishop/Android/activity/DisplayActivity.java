package com.xiaojishop.Android.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiaojishop.Android.DataKey;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.R;

/**
 * Created by admin on 2016/10/17.
 */
public class DisplayActivity extends BaseActvity {

    String TAG = "display";
    private WebView mWebWv;

    @Override
    protected int loadLayout() {
        return R.layout.activity_display;
    }

    @Override
    protected void init() {
        F();
        mWebWv.loadUrl(kingData.getData(DataKey.URL));
        mWebWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }

    @Override
    protected void onClickSet(int i) {

    }
}
