package com.shop.Android.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shop.Android.base.BaseActvity;
import com.shop.R;

/**
 * Created by admin on 2016/9/23.
 */
public class UserHelperActivity extends BaseActvity {
    private String TAG = "helper";
    private RelativeLayout mOrderRl;
    private ImageView mBottomIv;
    private WebView mOrderWv;
    private RelativeLayout mIntegralRl;
    private WebView mIntegralWv;
    private ImageView mFenIv;
    private RelativeLayout mOtherRl;
    private WebView mOtherWv;
    private ImageView mTopIv;

    @Override
    protected int loadLayout() {
        return R.layout.activity_user_helper;
    }

    @Override
    protected void initTitleBar() {
        initTitle("疑问帮助");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        setOnClicks(mOrderRl, mIntegralRl, mOtherRl);
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_helper_integral_rl:
                if (mIntegralWv.getVisibility() == View.VISIBLE) {
                    mIntegralWv.setVisibility(View.GONE);
                    mFenIv.setImageResource(R.mipmap.bottom3x);
                } else {
                    mIntegralWv.setVisibility(View.VISIBLE);
                    mFenIv.setImageResource(R.mipmap.top3x);
                }
                break;
            case R.id.ay_helper_order_rl:
                if (mOrderWv.getVisibility() == View.VISIBLE) {
                    mOrderWv.setVisibility(View.GONE);
                    mBottomIv.setImageResource(R.mipmap.bottom3x);
                } else {
                    mOrderWv.setVisibility(View.VISIBLE);
                    mBottomIv.setImageResource(R.mipmap.top3x);
                }
                break;
            case R.id.ay_helper_other_rl:
                if (mOtherWv.getVisibility()==View.VISIBLE){
                    mOtherWv.setVisibility(View.GONE);
                    mTopIv.setImageResource(R.mipmap.bottom3x);
                }else {
                    mOtherWv.setVisibility(View.VISIBLE);
                    mTopIv.setImageResource(R.mipmap.top3x);
                }
                break;
        }
    }
}
