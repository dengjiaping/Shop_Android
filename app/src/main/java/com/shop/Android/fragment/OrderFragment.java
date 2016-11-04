package com.shop.Android.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.shop.Android.Config;
import com.shop.Android.base.BaseFragment;
import com.shop.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/9/9.
 */
public class OrderFragment extends BaseFragment {
    private String TAG = "order";
    private RadioButton mNopayRb;
    private RadioButton mPayRb;
    private RadioButton mFinishRb;
    private RadioButton mCancelRb;
    private Map<String, Fragment> cache = new HashMap<String, Fragment>();

    @Override
    protected int loadLayout() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initTitleBar() {
        initTitle("订单中心");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
//        mTitleRightIv.setImageResource(R.drawable.search_white);
//        mTitleRightIv.setVisibility(View.VISIBLE);
    }


    @Override
    protected void init() {
        F();
        setOnClicks(mNopayRb,mPayRb,mFinishRb,mCancelRb);
        cache.put("mNopayRb",new WaitPayOrderFragment());
        cache.put("mPayRb",new PayOrderFragment());
        cache.put("mFinishRb",new OrderFinishFragment());
        cache.put("mCancelRb",new OrderCancelFragment());
        FragmentTransaction tx =  getFragmentManager().beginTransaction();
        tx.add(R.id.ft_order_frame_fl,cache.get("mNopayRb"));
        tx.add(R.id.ft_order_frame_fl,cache.get("mPayRb"));
        tx.add(R.id.ft_order_frame_fl,cache.get("mFinishRb"));
        tx.add(R.id.ft_order_frame_fl,cache.get("mCancelRb"));
        tx.show(cache.get("mNopayRb"));
        tx.hide(cache.get("mPayRb"));
        tx.hide(cache.get("mFinishRb"));
        tx.hide(cache.get("mCancelRb"));
        tx.commit();
        mTitleRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onClickSet(int i) {
        FragmentTransaction tx =  getFragmentManager().beginTransaction();
        switch (i){
            case R.id.ft_order_nopay_rb:
                tx.show(cache.get("mNopayRb"));
                tx.hide(cache.get("mPayRb"));
                tx.hide(cache.get("mFinishRb"));
                tx.hide(cache.get("mCancelRb"));
                kingData.sendBroadCast(Config.ORDER);
                break;
            case R.id.ft_order_pay_rb:
                tx.hide(cache.get("mNopayRb"));
                tx.show(cache.get("mPayRb"));
                tx.hide(cache.get("mFinishRb"));
                tx.hide(cache.get("mCancelRb"));
                kingData.sendBroadCast(Config.PAY_ORDER);
                break;
            case R.id.ft_order_finish_rb:
                tx.hide(cache.get("mNopayRb"));
                tx.hide(cache.get("mPayRb"));
                tx.show(cache.get("mFinishRb"));
                tx.hide(cache.get("mCancelRb"));
                kingData.sendBroadCast(Config.FINISH_ORDER);
                break;
            case R.id.ft_order_cancel_rb:
                tx.hide(cache.get("mNopayRb"));
                tx.hide(cache.get("mPayRb"));
                tx.hide(cache.get("mFinishRb"));
                tx.show(cache.get("mCancelRb"));
                kingData.sendBroadCast(Config.CANCEL_ORDER);
                break;
        }
        tx.commit();

    }
}
