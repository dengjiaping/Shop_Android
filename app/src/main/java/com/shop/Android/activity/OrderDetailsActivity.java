package com.shop.Android.activity;

import android.widget.TextView;

import com.shop.Android.base.BaseActvity;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class OrderDetailsActivity extends BaseActvity {
    private String TAG = "details";
    private TextView mName;
    private TextView mPhone;
    private TextView mAddress;
    private TextView mFeeTv;
    private TextView mPriceTv;
    private TextView mNumTv;
    private TextView mTimeTv;
    private TextView mContentTv;
    private TextView mSendTv;
    private TextView mPayTv;
    private TextView mCancelTv;
    @Override
    protected int loadLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initTitleBar() {
        initTitle("订单详情");
        mTitleLeftIv.setImageResource(R.mipmap.back);
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
    }

    @Override
    protected void init() {
      F();
    }

    @Override
    protected void onClickSet(int i) {

    }
}
