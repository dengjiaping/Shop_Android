package com.shop.Android.activity;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Utils.GsonUtil;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.NoScrollListView;
import com.shop.R;
import com.shop.ShopCar.Goods;
import com.shop.ShopCar.ShopCar;
import com.shop.ShopCar.TMShopCar;

/**
 * Created by admin on 2016/9/27.
 */
public class SubmitOrderActivity extends BaseActvity {

    //0特卖购物车
    //1普通购物车
    //2订单
    public static int TYPE = 0;

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
        fillData();
    }


    private void fillData() {
        switch (TYPE) {
            case 0:
                mTotalTv.setText("￥" + TMShopCar.allPrice());
                mActualTv.setText("￥" + TMShopCar.allPrice());
                mPriceTv.setText(mActualTv.getText().toString());
                goodsListAdapter = new GoodsListAdapter(TMShopCar.getMap().size(), R.layout.activity_submit_order_item, new GoodsListViewHolder());
                mScrollLv.setAdapter(goodsListAdapter);
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {

    }

    class GoodsListAdapter extends KingAdapter {

        public GoodsListAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            switch (TYPE) {
                case 0:
                    ((GoodsListViewHolder) o).mNameTv.setText(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(TMShopCar.getKeys().get(i)), Goods.class)).getTitle());
                    ((GoodsListViewHolder) o).mPriceTv.setText("￥" + ((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(TMShopCar.getKeys().get(i)), Goods.class)).getPrice());
                    ((GoodsListViewHolder) o).mNumTv.setText("x" + ((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(TMShopCar.getKeys().get(i)), Goods.class)).getCount());
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }

        }
    }

    class GoodsListViewHolder {
        String TAG = "submits";
        TextView mNameTv;
        TextView mNumTv;
        TextView mPriceTv;

    }
}
