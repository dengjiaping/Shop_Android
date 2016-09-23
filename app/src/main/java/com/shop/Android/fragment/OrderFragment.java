package com.shop.Android.fragment;

import android.view.View;
import android.widget.RadioButton;

import com.shop.Android.base.BaseFragment;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class OrderFragment extends BaseFragment {
    private String TAG = "order";
    private RadioButton mNopayRb;
    private RadioButton mPayRb;
    private RadioButton mFinishRb;
    private RadioButton mCancelRb;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initTitleBar() {
        initTitle("订单中心");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
        mTitleRightIv.setImageResource(R.drawable.search_white);
        mTitleRightIv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void init() {
        F();
        setOnClicks(mNopayRb,mPayRb,mFinishRb,mCancelRb);

    }

    @Override
    protected void onClickSet(int i) {
        switch (i){
            case R.id.ft_order_nopay_rb:

                break;
            case R.id.ft_order_pay_rb:

                break;
            case R.id.ft_order_finish_rb:

                break;
            case R.id.ft_order_cancel_rb:

                break;
        }

    }
}
