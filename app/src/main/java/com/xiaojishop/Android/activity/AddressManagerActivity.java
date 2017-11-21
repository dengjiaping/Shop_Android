package com.xiaojishop.Android.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Base.KingData;
import com.king.Utils.GsonUtil;
import com.xiaojishop.Android.Config;
import com.xiaojishop.Android.DataKey;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Android.widget.AnimNoLineRefreshListView;
import com.xiaojishop.Net.ActionKey;
import com.xiaojishop.Net.Bean.AddressBean;
import com.xiaojishop.Net.Bean.BaseBean;
import com.xiaojishop.Net.Param.DelAddressParam;
import com.xiaojishop.Net.Param.Token;
import com.xiaojishop.R;

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
        mListRv.setPullLoadEnable(false);
        kingData.registerWatcher(Config.ADD_ADDRESS, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                Post(ActionKey.ADDRESS_INDEX, new Token(), AddressBean.class);
            }
        });
        Post(ActionKey.ADDRESS_INDEX, new Token(), AddressBean.class);
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
                        Post(ActionKey.ADDRESS_INDEX, new Token(), AddressBean.class);
                    }
                },1000);

            }

            @Override
            public void onLoadMore() {

            }

//
        });
        mListRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mTitleRightTv.getText().toString().equals("管理")){
                    kingData.putData(DataKey.ADDRESS,GsonUtil.Bean2Str(addressBean.getData().get(i)));
                    kingData.sendBroadCast("ZZADDDRESS");
                    animFinsh();
                }
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
                    if (addressBean.getData() == null || addressBean.getData().size()==0) {
                        mNoneRl.setVisibility(View.VISIBLE);
                    } else {
                        mNoneRl.setVisibility(View.GONE);
                        addressAdapter = new AddressAdapter(addressBean.getData().size(), R.layout.activity_address_item, new AddressViewHolder());
                        mListRv.setAdapter(addressAdapter);
                    }
                } else if (addressBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(addressBean.getMsg());
                }
                break;
            case ActionKey.DEL_ADDRESS:
                BaseBean bean = (BaseBean) result;
                if (bean.getCode()==200){
                    ToastInfo("删除成功");
                    kingData.sendBroadCast(Config.ADD_ADDRESS);
                }else if (bean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(bean.getMsg());
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
        public void padData(final int i, Object o) {
            final AddressViewHolder addressViewHolder = (AddressViewHolder) o;
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
            addressViewHolder.mAreaTv.setText(addressBean.getData().get(i).getCity().getName()+addressBean.getData().get(i).getArea().getName()+addressBean.getData().get(i).getVillage().getName());
            addressViewHolder.mDetailsTv.setText(addressBean.getData().get(i).getUnit().getName()+addressBean.getData().get(i).getFloor().getName()+addressBean.getData().get(i).getRoom().getName());

            addressViewHolder.mDelIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Post(ActionKey.DEL_ADDRESS,new DelAddressParam(addressBean.getData().get(i).getId()), BaseBean.class);
                }
            });

            addressViewHolder.mEditRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,EditorAddressActivity.class);
                    intent.putExtra("type",1);
                    intent.putExtra("address", GsonUtil.Bean2Str(addressBean.getData().get(i)));
                    startActivity(intent);
                    overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);

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
