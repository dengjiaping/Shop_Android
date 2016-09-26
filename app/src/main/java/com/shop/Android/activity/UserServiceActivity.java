package com.shop.Android.activity;

import com.shop.Android.base.BaseActvity;
import com.shop.R;

/**
 * Created by admin on 2016/9/23.
 */
public class UserServiceActivity extends BaseActvity {
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

    }

    @Override
    protected void onClickSet(int i) {

    }
}
