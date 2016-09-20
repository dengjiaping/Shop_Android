package com.shop.Android.activity;

import com.shop.Android.base.BaseActvity;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.widget.RefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/12.
 */
public class MsgActivity extends BaseActvity {

    private String TAG = "msg";
    private RefreshListView mContentRlv;

    @Override
    protected int loadLayout() {
        return R.layout.activity_msg;
    }


    @Override
    protected void init() {
        initTitle("消息中心");
        F();
        mContentRlv.setAdapter(new TestAdapter(10, R.layout.item_ay_msg));
    }

    @Override
    protected void onClickSet(int i) {

    }
}
