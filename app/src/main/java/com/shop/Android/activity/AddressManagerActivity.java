package com.shop.Android.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/21.
 */
public class AddressManagerActivity extends BaseActvity {
    private String TAG = "address";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mAddRl;
    private RelativeLayout mNoneRl;
    private LinearLayout mDatasLl;
    private int type;
    private boolean isComplete = false;

    private AddressAdapter addressAdapter;

    @Override
    protected int loadLayout() {
        return R.layout.activity_address;
    }

    @Override
    protected void initTitleBar() {
        initTitle("收货地址");
        mTitleLeftIv.setImageResource(R.mipmap.back);
        mTitleRightTv.setText("管理");
        mTitleRightTv.setVisibility(View.VISIBLE);
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
    }



    @Override
    protected void init() {
        F();
        mNoneRl.setVisibility(View.GONE);
        addressAdapter = new AddressAdapter(3, R.layout.activity_address_item, new AddressViewHolder());
        mListRv.setAdapter(addressAdapter);
        setOnClicks(mAddRl);
        mTitleRightTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isComplete = !isComplete;
                if (isComplete){
                    mTitleRightTv.setText("完成");
                    mListRv.setAdapter(addressAdapter);
                }else {
                    mTitleRightTv.setText("管理");
                    mListRv.setAdapter(addressAdapter);
                }

            }
        });
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_address_add_rl:
                openActivity(EditorAddressActivity.class);
                break;
        }
    }

    class AddressAdapter extends KingAdapter {

        public AddressAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            AddressViewHolder addressViewHolder = (AddressViewHolder) o;
            if (isComplete) {
                //显示
                addressViewHolder.mEditRl.setVisibility(View.VISIBLE);
                addressViewHolder.mDelIv.setVisibility(View.VISIBLE);
                addressViewHolder.mDefaultTv.setVisibility(View.GONE);
            } else {
                //隐藏
                addressViewHolder.mEditRl.setVisibility(View.GONE);
                addressViewHolder.mDelIv.setVisibility(View.GONE);
                addressViewHolder.mDefaultTv.setVisibility(View.GONE);
            }

        }
    }

    class AddressViewHolder {
        String TAG = "address";
        TextView mNameTv;
        TextView mPhoneTv;
        TextView mAreaTv;
        TextView mDetailsTv;
        TextView mDefaultTv;
        RelativeLayout mEditRl;
        ImageView mDelIv;
    }
}
