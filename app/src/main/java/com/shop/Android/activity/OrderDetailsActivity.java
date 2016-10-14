package com.shop.Android.activity;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingData;
import com.shop.Android.Config;
import com.shop.Android.DataKey;
import com.shop.Android.base.BaseActvity;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.OrderDetailsBean;
import com.shop.Net.Param.OrderDetailsParam;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class OrderDetailsActivity extends BaseActvity {
    private String TAG = "details";
    private TextView mNameTv;
    private TextView mPhoneTv;
    private TextView mAddressTv;
    private TextView mFeeTv;
    private TextView mPriceTv;
    private TextView mNumTv;
    private TextView mTimeTv;
    private TextView mContentTv;
    private TextView mSendTv;
    private TextView mPayTv;
    private TextView mCancelTv;
    private LinearLayout mWaitLl;
    private RelativeLayout mCancelRl;
    private RelativeLayout mDistributionRl;
    private RelativeLayout mUndistributionxRl;
    private RelativeLayout mFinishRl;
    private RelativeLayout mEvaluationRl;
    private String id = "";
    private OrderDetailsBean orderDetailsBean;

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
        kingData.registerWatcher(Config.ORDER_ID, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                id = kingData.getData(DataKey.ORDER);

            }
        });
        Post(ActionKey.ORDER_DETAILS,new OrderDetailsParam(id), OrderDetailsBean.class);

        setOnClicks(mPayTv,mCancelTv);
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.ORDER_DETAILS:
                orderDetailsBean = (OrderDetailsBean) result;
                if (orderDetailsBean.getCode()==200){
                    if (orderDetailsBean.getData()!=null){

                    }
                }else if (orderDetailsBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(orderDetailsBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_details_pay_tv:
                openActivity(SubmitOrderActivity.class);
                break;
            case R.id.ay_details_cancel_tv:
                break;
        }
    }
}
