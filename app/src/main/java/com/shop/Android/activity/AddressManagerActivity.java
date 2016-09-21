package com.shop.Android.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.RefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/21.
 */
public class AddressManagerActivity extends BaseActvity {
    private String TAG = "address";
    private RefreshListView mListRv;
    private RelativeLayout mAddRl;
    private RelativeLayout mNone;
    private LinearLayout mDatasLl;
    private AddressAdapter addressAdapter;

    @Override
    protected int loadLayout() {
        return R.layout.activity_address;
    }

    @Override
    protected void initTitleBar() {
        initTitle("收货地址");
        mTitleLeftIv.setImageResource(R.mipmap.back);
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
        addressAdapter = new AddressAdapter(3,R.layout.activity_address_item,new AddressViewHolder());
        mListRv.setAdapter(addressAdapter);
    }

    @Override
    protected void init() {
        F();
        mNone.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onClickSet(int i) {

    }

    class AddressAdapter extends KingAdapter {

        public AddressAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {

        }
    }
    class AddressViewHolder{
        String TAG = "address";
        TextView mNameTv;
        TextView mPhoneTv;
        TextView mAreaTv;
        TextView mDetailsTv;
        TextView mDefaultTv;
    }
}
