package com.shop.Android.activity;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.NoScrollListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/27.
 */
public class SubmitOrderActivity extends BaseActvity {
    private String TAG = "submit";
    private LinearLayout mAddLl;
    private RelativeLayout mAddRl;
    private RelativeLayout mAddressRl;
    private TextView mNameTv;
    private TextView mPhoneTv;
    private TextView mAddressTv;
    private TextView mDetailsTv;
    private RadioButton mAlipayRb;
    private RadioButton mWechatRb;
    private RadioButton mPayRb;
    private TextView mShopTv;
    private NoScrollListView mScrollLv;
    private TextView mFeeTv;
    private TextView mPreferentialTv;
    private TextView mTotalTv;
    private TextView mActualTv;
    private EditText mContentEt;
    private TextView mPriceTv;
    private RelativeLayout mOrderRl;
    private GoodsListAdapter goodsListAdapter;

    @Override
    protected int loadLayout() {
        return R.layout.activity_submit_order;
    }

    @Override
    protected void initTitleBar() {
        initTitle("订单");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        goodsListAdapter = new GoodsListAdapter(2,R.layout.activity_submit_order_item,new GoodsListViewHolder());
        mScrollLv.setAdapter(goodsListAdapter);

    }

    @Override
    protected void onClickSet(int i) {

    }
    class GoodsListAdapter extends KingAdapter{

        public GoodsListAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {

        }
    }
    class GoodsListViewHolder {
        String TAG = "submits";
        TextView mNameTv;
        TextView mNumTv;
        TextView mPriceTv;

    }
}
