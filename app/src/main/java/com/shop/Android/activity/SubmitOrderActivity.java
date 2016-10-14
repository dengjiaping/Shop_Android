package com.shop.Android.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Base.KingData;
import com.king.Utils.GsonUtil;
import com.king.Utils.SPrefUtil;
import com.shop.Android.DataKey;
import com.shop.Android.SPKey;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.NoScrollListView;
import com.shop.wxapi.WXPayEntryActivity;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.AddressBean;
import com.shop.Net.Bean.FeeBean;
import com.shop.Net.Bean.IndexBean;
import com.shop.Net.Bean.OrderInfoBean;
import com.shop.Net.Param.Order;
import com.shop.Net.Param.Token;
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
    private TextView mLowestTv;
    private GoodsListAdapter goodsListAdapter;

    @Override
    protected int loadLayout() {
        Post(ActionKey.FEE, new Token(), FeeBean.class);
        return R.layout.activity_submit_order;
    }

    @Override
    protected void initTitleBar() {
        initTitle("订单");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    private boolean isGetAddress = false;

    @Override
    protected void init() {
        F();
        mShopTv.setText(((IndexBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX, ""), IndexBean.class)).getData().getShop().getName());
        kingData.registerWatcher("ZZADDDRESS", new KingData.KingCallBack() {
            @Override
            public void onChange() {
                isGetAddress = true;
                mAddRl.setVisibility(View.GONE);
                mAddressRl.setVisibility(View.VISIBLE);
                AddressBean.DataBean dataBean = GsonUtil.Str2Bean(kingData.getData(DataKey.ADDRESS), AddressBean.DataBean.class);
                mNameTv.setText(dataBean.getContact());
                mPhoneTv.setText(dataBean.getPhone());
                mDetailsTv.setText(dataBean.getBuild().getName() + dataBean.getUnit().getName() + dataBean.getFloor().getName() + dataBean.getRoom().getName());
                mAddressTv.setText(dataBean.getCity().getName() + dataBean.getArea().getName() + dataBean.getVillage().getName());
            }
        });

        setOnClicks(mAddLl, mOrderRl);
    }

    private double lowest = 0;
    private double fee = 0;

    private void fillData() {
        switch (TYPE) {
            case 0:
                mTotalTv.setText("￥" + TMShopCar.allPrice());
                mActualTv.setText("￥" + (Double.valueOf(TMShopCar.allPrice()) > lowest ? (Double.valueOf(TMShopCar.allPrice())) : (Double.valueOf(TMShopCar.allPrice())) + fee));
                mPriceTv.setText(mActualTv.getText().toString());
                goodsListAdapter = new GoodsListAdapter(TMShopCar.getMap().size(), R.layout.activity_submit_order_item, new GoodsListViewHolder());
                mScrollLv.setAdapter(goodsListAdapter);
                break;
            case 1:
                mTotalTv.setText("￥" + ShopCar.allPrice());
                mActualTv.setText("￥" + (Double.valueOf(ShopCar.allPrice()) > lowest ? (Double.valueOf(ShopCar.allPrice())) : (Double.valueOf(ShopCar.allPrice())) + fee));
                mPriceTv.setText(mActualTv.getText().toString());
                goodsListAdapter = new GoodsListAdapter(ShopCar.isInValid(), R.layout.activity_submit_order_item, new GoodsListViewHolder());
                mScrollLv.setAdapter(goodsListAdapter);
                break;
            case 2:
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_submit_add_ll:
                openActivity(AddressManagerActivity.class);
                break;
            case R.id.ay_submit_order_rl:
                switch (TYPE) {
                    case 0:
                        if (!isGetAddress) {
                            ToastInfo("请先添加收货地址");
                        } else {
                            Order order = new Order(1 + "", TMShopCar.commit(), getText(mAddressTv) + getText(mDetailsTv), getText(mPriceTv).substring(1), getText(mContentEt), getText(mNameTv), getText(mPhoneTv));
                            Post(ActionKey.COMMITORDER, order, OrderInfoBean.class);
                        }
                        break;
                    case 1:
                        if (!isGetAddress) {
                            ToastInfo("请先添加收货地址");
                        } else {
                            Order order = new Order(0 + "", ShopCar.commit(), getText(mAddressTv) + getText(mDetailsTv), getText(mPriceTv).substring(1), getText(mContentEt), getText(mNameTv), getText(mPhoneTv));
                            Post(ActionKey.COMMITORDER, order, OrderInfoBean.class);
                        }
                        break;
                    case 2:
                        break;
                }
                break;
        }
    }

    private OrderInfoBean orderInfoBean;

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.COMMITORDER:
                switch (TYPE) {
                    case 0:
                        orderInfoBean = (OrderInfoBean) result;
                        if (orderInfoBean.getCode() == 200) {
                            kingData.putData(DataKey.PRICE, getText(mActualTv));
                            kingData.putData(DataKey.ID, orderInfoBean.getData().getOrderinfo().getId());
                            kingData.putData(DataKey.NUMBER, orderInfoBean.getData().getOrderinfo().getNumber());
                            kingData.putData(DataKey.TIME,orderInfoBean.getData().getOrderinfo().getEnd_time());
                            kingData.sendBroadCast("ZZREFRESHPAY");
                            openActivity(WXPayEntryActivity.class);
                        } else {
                            ToastInfo(orderInfoBean.getMsg());
                        }
                        break;
                    case 1:
                        orderInfoBean = (OrderInfoBean) result;
                        if (orderInfoBean.getCode() == 200) {
                            kingData.putData(DataKey.PRICE, getText(mActualTv));
                            kingData.putData(DataKey.ID, orderInfoBean.getData().getOrderinfo().getId());
                            kingData.putData(DataKey.NUMBER, orderInfoBean.getData().getOrderinfo().getNumber());
                            kingData.putData(DataKey.TIME,orderInfoBean.getData().getOrderinfo().getEnd_time());
                            kingData.sendBroadCast("ZZREFRESHPAY");
                            openActivity(WXPayEntryActivity.class);
                        } else {
                            ToastInfo(orderInfoBean.getMsg());
                        }
                        break;
                    case 2:
                        break;
                }
                break;
            case ActionKey.FEE:
                FeeBean bean = (FeeBean) result;
                if (bean.getCode() == 2001) {
                    ToastInfo(bean.getMsg());
                    openActivity(LoginActivity.class);
                } else {
                    lowest = Double.valueOf(bean.getData().getLowest());
                    fee = Double.valueOf(bean.getData().getFreight());
                    mFeeTv.setText("￥" + bean.getData().getFreight());
                    mLowestTv.setText("满" + bean.getData().getLowest() + "元免配送费");
                    mDeliveryTv.setText(bean.getData().getSend_str());
                    fillData();
                }
                break;
        }
    }

    private TextView mDeliveryTv;

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
                    ((GoodsListViewHolder) o).mNameTv.setText(((Goods) GsonUtil.Str2Bean(ShopCar.getValidMap().get(ShopCar.getValidKeys().get(i)), Goods.class)).getTitle());
                    ((GoodsListViewHolder) o).mPriceTv.setText("￥" + ((Goods) GsonUtil.Str2Bean(ShopCar.getValidMap().get(ShopCar.getValidKeys().get(i)), Goods.class)).getPrice());
                    ((GoodsListViewHolder) o).mNumTv.setText("x" + ((Goods) GsonUtil.Str2Bean(ShopCar.getValidMap().get(ShopCar.getValidKeys().get(i)), Goods.class)).getCount());
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
