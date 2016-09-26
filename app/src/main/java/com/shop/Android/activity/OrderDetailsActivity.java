package com.shop.Android.activity;

import com.shop.Android.base.BaseActvity;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class OrderDetailsActivity extends BaseActvity {
    @Override
    protected int loadLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initTitleBar() {
        initTitle("订单详情");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void onClickSet(int i) {

    }
}
