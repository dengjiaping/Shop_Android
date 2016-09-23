package com.shop.Android.activity;

import com.shop.Android.base.BaseActvity;
import com.shop.R;

/**
 * Created by admin on 2016/9/23.
 */
public class UserAgreementActivity extends BaseActvity {

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

    }

    @Override
    protected void onClickSet(int i) {

    }
}
