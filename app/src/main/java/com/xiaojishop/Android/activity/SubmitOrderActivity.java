package com.xiaojishop.Android.activity;

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
import com.xiaojishop.Android.Config;
import com.xiaojishop.Android.DataKey;
import com.xiaojishop.Android.SPKey;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Android.widget.NoScrollListView;
import com.xiaojishop.wxapi.WXPayEntryActivity;
import com.xiaojishop.Net.ActionKey;
import com.xiaojishop.Net.Bean.AddressBean;
import com.xiaojishop.Net.Bean.FeeBean;
import com.xiaojishop.Net.Bean.IndexBean;
import com.xiaojishop.Net.Bean.OrderInfoBean;
import com.xiaojishop.Net.Param.Order;
import com.xiaojishop.Net.Param.Token;
import com.xiaojishop.R;
import com.xiaojishop.ShopCar.Goods;
import com.xiaojishop.ShopCar.ShopCar;
import com.xiaojishop.ShopCar.TMShopCar;

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
                            kingData.sendBroadCast(Config.ORDER);
                        }
                        break;
                    case 1:
                        if (!isGetAddress) {
                            ToastInfo("请先添加收货地址");
                        } else {
                            Order order = new Order(0 + "", ShopCar.commit(), getText(mAddressTv) + getText(mDetailsTv), getText(mPriceTv).substring(1), getText(mContentEt), getText(mNameTv), getText(mPhoneTv));
                            Post(ActionKey.COMMITORDER, order, OrderInfoBean.class);
                            kingData.sendBroadCast(Config.ORDER);
                        }
                        break;
                    case 2:
                        break;
                }
                break;
        }
    }

    private Goods thing = new Goods();
    private OrderInfoBean orderInfoBean;
    private boolean isAdd = false;
    private boolean isDelete = false;

    @Override
    public void onSuccess(String what, Object result) {
        String msg = "";
        switch (what) {
            case ActionKey.COMMITORDER:
                switch (TYPE) {
                    case 0:
                        orderInfoBean = (OrderInfoBean) result;
                        if (orderInfoBean.getCode() == 200) {
                            kingData.putData(DataKey.PRICE, getText(mActualTv));
                            kingData.putData(DataKey.ID, orderInfoBean.getData().getOrderinfo().getId());
                            kingData.putData(DataKey.NUMBER, orderInfoBean.getData().getOrderinfo().getNumber());
                            kingData.putData(DataKey.TIME, orderInfoBean.getData().getOrderinfo().getEnd_time());
                            kingData.putData(DataKey.TYPE, "0");
                            kingData.sendBroadCast("ZZREFRESHPAY");
                            animFinsh();
                            openActivity(WXPayEntryActivity.class);
                        } else if (orderInfoBean.getCode() == 405) {
                            for (int i = 0; i < orderInfoBean.getData().getFail().size(); i++) {
                                thing.setId(orderInfoBean.getData().getFail().get(i).getId());
                                thing.setImage(orderInfoBean.getData().getFail().get(i).getImage());
                                thing.setCount(orderInfoBean.getData().getFail().get(i).getStock());
                                thing.setTitle(orderInfoBean.getData().getFail().get(i).getTitle());
                                thing.setSubTitle(orderInfoBean.getData().getFail().get(i).getSubtitled());
                                thing.setPrice(orderInfoBean.getData().getFail().get(i).getPrice());
                                switch (orderInfoBean.getData().getFail().get(i).getErr()) {
                                    case 2://库存不足
                                        msg = msg + thing.getTitle() + "库存不足,已调整到最大库存;";
                                        thing.setCount(orderInfoBean.getData().getFail().get(i).getNewcount() + "");
                                        thing.setMaxNum(orderInfoBean.getData().getFail().get(i).getNewcount() + "");
                                        break;
                                    case 3://价格变动
                                        msg = msg + thing.getTitle() + "价格变动,已做相应调整;";
                                        thing.setPrice(orderInfoBean.getData().getFail().get(i).getNewprice() + "");
                                        break;
                                    case 4://库存不足价格变动
                                        msg = msg + thing.getTitle() + "库存不足,价格变动,已调整到最大库存,价格也做相应调整;";
                                        thing.setCount(orderInfoBean.getData().getFail().get(i).getNewcount() + "");
                                        thing.setPrice(orderInfoBean.getData().getFail().get(i).getNewprice() + "");
                                        thing.setMaxNum(orderInfoBean.getData().getFail().get(i).getNewcount() + "");
                                        break;
                                    case 5://下架删除  不会出现
                                        msg = msg.isEmpty() ? "特卖商品" + thing.getTitle() + "已经下架,已从当前购物车移除" : msg + ",特卖商品" + thing.getTitle() + "已经下架,已从当前购物车移除";
                                        TMShopCar.delete(thing.getId());
                                        isDelete = true;
                                        if (TMShopCar.getMap().size() == 0) {
                                            msg = msg + ",当前购物车没有物品了,请去选购!";
                                            animFinsh();
                                        }
                                        break;
                                }
                                if (!msg.isEmpty()) {
                                    isAdd = true;
                                }
                                if (!isDelete) {
                                    TMShopCar.mergeButNotAdd(thing, isAdd);
                                    isDelete = false;
                                }
                                isAdd = false;
                            }
                            fillData();
                            ToastInfo(msg);
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
                            kingData.putData(DataKey.TIME, orderInfoBean.getData().getOrderinfo().getEnd_time());
                            kingData.putData(DataKey.TYPE, "1");
                            kingData.sendBroadCast("ZZREFRESHPAY");
                            kingData.sendBroadCast("CAR");
                            animFinsh();
                            openActivity(WXPayEntryActivity.class);
                        } else if (orderInfoBean.getCode() == 405) {
                            for (int i = 0; i < orderInfoBean.getData().getFail().size(); i++) {
                                thing.setId(orderInfoBean.getData().getFail().get(i).getId());
                                thing.setImage(orderInfoBean.getData().getFail().get(i).getImage());
                                thing.setCount(orderInfoBean.getData().getFail().get(i).getStock());
                                thing.setTitle(orderInfoBean.getData().getFail().get(i).getTitle());
                                thing.setSubTitle(orderInfoBean.getData().getFail().get(i).getSubtitled());
                                thing.setPrice(orderInfoBean.getData().getFail().get(i).getPrice());
                                switch (orderInfoBean.getData().getFail().get(i).getErr()) {
                                    case 2://库存不足
                                        msg = msg + thing.getTitle() + "库存不足,已调整到最大库存;";
                                        thing.setCount(orderInfoBean.getData().getFail().get(i).getNewcount() + "");
                                        thing.setMaxNum(orderInfoBean.getData().getFail().get(i).getNewcount() + "");
                                        break;
                                    case 3://价格变动
                                        msg = msg + thing.getTitle() + "价格变动,已做相应调整;";
                                        thing.setPrice(orderInfoBean.getData().getFail().get(i).getNewprice() + "");
                                        break;
                                    case 4://库存不足价格变动
                                        msg = msg + thing.getTitle() + "库存不足,价格变动,已调整到最大库存,价格也做相应调整;";
                                        thing.setCount(orderInfoBean.getData().getFail().get(i).getNewcount() + "");
                                        thing.setPrice(orderInfoBean.getData().getFail().get(i).getNewprice() + "");
                                        thing.setMaxNum(orderInfoBean.getData().getFail().get(i).getNewcount() + "");
                                        break;
                                }
                                if (!msg.isEmpty()) {
                                    isAdd = true;
                                }
                                ShopCar.mergeButNotAdd(thing, isAdd);
                                isAdd = false;
                            }
                            fillData();
                            ToastInfo(msg);
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
