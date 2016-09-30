package com.shop.Android.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Utils.GsonUtil;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.AddressBean;
import com.shop.Net.Param.AddressParam;
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
    private AddressBean addressBean;

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
        Post(ActionKey.ADDRESS_INDEX, new AddressParam("c521bb2d5b5f5e309046e04f67ef5bc3"), AddressBean.class);
        setOnClicks(mAddRl);
        mTitleRightTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isComplete = !isComplete;
                if (isComplete) {
                    mTitleRightTv.setText("完成");
                    mListRv.setAdapter(addressAdapter);
                } else {
                    mTitleRightTv.setText("管理");
                    mListRv.setAdapter(addressAdapter);
                }

            }
        });
        mListRv.setListener(new AnimNoLineRefreshListView.onListener(){

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Post(ActionKey.ADDRESS_INDEX, new AddressParam("c521bb2d5b5f5e309046e04f67ef5bc3"), AddressBean.class);
                    }
                },1000);

            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Post(ActionKey.ADDRESS_INDEX, new AddressParam("c521bb2d5b5f5e309046e04f67ef5bc3"), AddressBean.class);
                    }
                },1000);
            }
        });
        mListRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListRv.setPullLoadEnable(false);
                Intent intent = new Intent(mContext,EditorAddressActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("address", GsonUtil.Bean2Str(addressBean.getData().get(i)));
                startActivity(intent);
                overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       mListRv.setPullLoadEnable(true);
                    }
                },200);
            }
        });
    }

    @Override
    public void onSuccess(String what, Object result) {
        mListRv.onRefreshComplete();
        mListRv.onLoadComplete();
        switch (what) {
            case ActionKey.ADDRESS_INDEX:
                addressBean = (AddressBean) result;
                if (addressBean.getCode() == 200) {
                    if (addressBean != null) {
                        mNoneRl.setVisibility(View.GONE);
                        addressAdapter = new AddressAdapter(addressBean.getData().size(), R.layout.activity_address_item, new AddressViewHolder());
                        mListRv.setAdapter(addressAdapter);
                    } else {
                        mNoneRl.setVisibility(View.VISIBLE);
                    }
                } else {
                  ToastInfo(addressBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_address_add_rl:
                Intent intent = new Intent(mContext,EditorAddressActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
                overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);
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
            if (addressBean.getData().get(i).getIs_default().equals("0")) {
                addressViewHolder.mDefaultTv.setVisibility(View.GONE);
            } else {
                addressViewHolder.mDefaultTv.setVisibility(View.VISIBLE);
            }
            addressViewHolder.mNameTv.setText(addressBean.getData().get(i).getContact());
            addressViewHolder.mPhoneTv.setText(addressBean.getData().get(i).getPhone());
            addressViewHolder.mAreaTv.setText(addressBean.getData().get(i).getCity()+addressBean.getData().get(i).getArea()+addressBean.getData().get(i).getVillage());
            addressViewHolder.mDetailsTv.setText(addressBean.getData().get(i).getUnit()+addressBean.getData().get(i).getFloor()+addressBean.getData().get(i).getRoom()+"室");

            addressViewHolder.mDelIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastInfo("123");
                }
            });

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
