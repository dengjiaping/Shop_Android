package com.shop.Android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Base.KingData;
import com.king.Dialog.CustomDialog;
import com.king.Internet.user_interface.xCallback;
import com.king.Internet.user_method.CallServer;
import com.shop.Android.Config;
import com.shop.Android.DataKey;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.TimeTextView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.OrderDetailsBean;
import com.shop.Net.Param.OrderDetailsParam;
import com.shop.R;
import com.shop.Utils.TimeUtils;
import com.shop.wxapi.WXPayEntryActivity;

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
    private TimeTextView mTimesTv;
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
    private LinearLayout mTimesLl;
    private NoScrollListView mListSv;
    private TextView mOrderTv;
    private String id = "";
    private OrderDetailsBean orderDetailsBean;
    private DetailsGoodsAdapter detailsGoodsAdapter;

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
                Post(ActionKey.ORDER_DETAILS, new OrderDetailsParam(id), OrderDetailsBean.class);
            }
        });

        setOnClicks(mPayTv, mCancelTv);


    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.ORDER_DETAILS:
                orderDetailsBean = (OrderDetailsBean) result;
                if (orderDetailsBean.getCode() == 200) {
                    if (orderDetailsBean.getData() != null) {
                        switch (orderDetailsBean.getData().getStatus()) {
                            case 1:
                                mOrderTv.setText(orderDetailsBean.getData().getTotal_price() + "元");
                                mWaitLl.setVisibility(View.VISIBLE);
                                mDistributionRl.setVisibility(View.GONE);
                                mUndistributionxRl.setVisibility(View.GONE);
                                mFinishRl.setVisibility(View.GONE);
                                mCancelRl.setVisibility(View.GONE);
                                mTimesLl.setVisibility(View.VISIBLE);
                                if (!orderDetailsBean.getData().getEnd_time().equals("")) {
                                    int time = (int) (TimeUtils.date2unix2(orderDetailsBean.getData().getEnd_time()) - TimeUtils.now());
                                    int hour = time / 60 / 60;
                                    int min = (time - (hour * 60 * 60)) / 60;
                                    int sec = time - (hour * 60 * 60) - (min * 60);


                                    mTimesTv.setTimes(new long[]{hour, min, sec});
                                    if (!mTimesTv.isRun()) {
                                        mTimesTv.run();
                                    }
                                }
                                mCancelTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Post(ActionKey.CANCEL_ORDER, new OrderDetailsParam(orderDetailsBean.getData().getId()), BaseBean.class);
                                    }
                                });
                                mPayTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        kingData.putData(DataKey.ID, orderDetailsBean.getData().getId());
                                        kingData.putData(DataKey.PRICE, orderDetailsBean.getData().getTotal_price());
                                        kingData.putData(DataKey.TIME, orderDetailsBean.getData().getEnd_time());
                                        kingData.sendBroadCast("ZZREFRESHPAY");
                                        openActivity(WXPayEntryActivity.class);
                                    }
                                });
                                break;
                            case 2:
                                mWaitLl.setVisibility(View.VISIBLE);
                                mDistributionRl.setVisibility(View.GONE);
                                mUndistributionxRl.setVisibility(View.GONE);
                                mFinishRl.setVisibility(View.GONE);
                                mCancelRl.setVisibility(View.GONE);
                                mTimesLl.setVisibility(View.GONE);
                                mPayTv.setText("确认收货");
                                mCancelTv.setVisibility(View.GONE);
                                break;
                            case 3:
                                mDistributionRl.setVisibility(View.VISIBLE);
                                mWaitLl.setVisibility(View.GONE);
                                mUndistributionxRl.setVisibility(View.GONE);
                                mFinishRl.setVisibility(View.GONE);
                                mCancelRl.setVisibility(View.GONE);
                                mTimesLl.setVisibility(View.GONE);
                                mPayTv.setText("确认收货");
                                mCancelTv.setVisibility(View.GONE);
                                mPayTv.setVisibility(View.VISIBLE);
                                break;
                            case 4:
                                mUndistributionxRl.setVisibility(View.VISIBLE);
                                mDistributionRl.setVisibility(View.GONE);
                                mWaitLl.setVisibility(View.GONE);
                                mFinishRl.setVisibility(View.GONE);
                                mCancelRl.setVisibility(View.GONE);
                                mTimesLl.setVisibility(View.GONE);
                                mCancelTv.setVisibility(View.GONE);
                                mPayTv.setVisibility(View.GONE);
                                break;
                            case 5:
                                mFinishRl.setVisibility(View.VISIBLE);
                                mUndistributionxRl.setVisibility(View.GONE);
                                mDistributionRl.setVisibility(View.GONE);
                                mWaitLl.setVisibility(View.GONE);
                                mCancelRl.setVisibility(View.GONE);
                                mCancelTv.setVisibility(View.GONE);
                                mPayTv.setText("评价商品");
                                mPayTv.setVisibility(View.VISIBLE);
                                mTimesLl.setVisibility(View.GONE);
                                mCancelTv.setVisibility(View.GONE);
                                break;
                            case 6:
                                mCancelRl.setVisibility(View.VISIBLE);
                                mFinishRl.setVisibility(View.GONE);
                                mUndistributionxRl.setVisibility(View.GONE);
                                mDistributionRl.setVisibility(View.GONE);
                                mWaitLl.setVisibility(View.GONE);
                                mTimesLl.setVisibility(View.GONE);
                                mPayTv.setVisibility(View.GONE);
                                mCancelTv.setText("删除订单");
                                mCancelTv.setVisibility(View.VISIBLE);
                                break;
                            case 7:
                                mFinishRl.setVisibility(View.VISIBLE);
                                mUndistributionxRl.setVisibility(View.GONE);
                                mDistributionRl.setVisibility(View.GONE);
                                mWaitLl.setVisibility(View.GONE);
                                mCancelRl.setVisibility(View.GONE);
                                mCancelTv.setVisibility(View.GONE);
                                mTimesLl.setVisibility(View.GONE);
                                mPayTv.setVisibility(View.VISIBLE);
                                mCancelTv.setVisibility(View.VISIBLE);
                                mPayTv.setText("查看评价");
                                break;
                        }

                        mAddressTv.setText(orderDetailsBean.getData().getReceiver_address());
                        mPhoneTv.setText(orderDetailsBean.getData().getReceiver_phone());
                        mFeeTv.setText("￥" + orderDetailsBean.getData().getExpenses());
                        mNameTv.setText(orderDetailsBean.getData().getReceiver_contacts());
                        mNumTv.setText(orderDetailsBean.getData().getOrder_number());
                        mPriceTv.setText("￥" + orderDetailsBean.getData().getTotal_price());
                        mContentTv.setText(orderDetailsBean.getData().getRemark());
                        mTimeTv.setText(orderDetailsBean.getData().getCreated_time());
                        mSendTv.setText(orderDetailsBean.getData().getSend_time());
                        detailsGoodsAdapter = new DetailsGoodsAdapter(orderDetailsBean.getData().getGoods().size(), R.layout.item_order_goods, new DetailsViewHolder());
                        mListSv.setAdapter(detailsGoodsAdapter);


                    }
                } else if (orderDetailsBean.getCode() == 2001) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(orderDetailsBean.getMsg());
                }
                break;

            case ActionKey.CANCEL_ORDER+ "CANCEL":
                BaseBean baseBean = (BaseBean) result;
                if (200 == baseBean.getCode()) {
                    ToastInfo("取消成功");
                    kingData.sendBroadCast(Config.CANCEL_ORDER);
                    animFinsh();
                } else if (2001 == baseBean.getCode()) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {

                }
                break;
            case ActionKey.ORDER_COMPLETE + "WAIT":
                BaseBean bean = (BaseBean) result;
                if (200 == bean.getCode()) {
                    ToastInfo("确认收货成功");
                    kingData.sendBroadCast(Config.ORDER);
                    animFinsh();
                } else if (2001 == bean.getCode()) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(bean.getMsg());
                }
                break;
            case ActionKey.ORDER_COMPLETE + "FINISH":
                BaseBean bean3 = (BaseBean) result;
                if (200 == bean3.getCode()) {
                    ToastInfo("确认收货成功");
                    kingData.sendBroadCast(Config.FINISH_ORDER);
                    animFinsh();
                } else if (2001 == bean3.getCode()) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(bean3.getMsg());
                }
                break;
            case ActionKey.DEL_ORDER + "SEVEN":
                BaseBean bean1 = (BaseBean) result;
                if (200 == bean1.getCode()) {
                    ToastInfo("删除成功");
                    kingData.sendBroadCast(Config.FINISH_ORDER);
                    animFinsh();
                } else if (2001 == bean1.getCode()) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(bean1.getMsg());
                }
                break;
            case ActionKey.DEL_ORDER + "DELETE":
                BaseBean bean2 = (BaseBean) result;
                if (200 == bean2.getCode()) {
                    ToastInfo("删除成功");
                    kingData.sendBroadCast(Config.CANCEL_ORDER);
                    animFinsh();
                } else if (2001 == bean2.getCode()) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(bean2.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {
        Intent intent;
        switch (i) {
            case R.id.ay_details_pay_tv:
                switch (orderDetailsBean.getData().getStatus()) {
                    case 1://未付款
                        kingData.putData(DataKey.ID, orderDetailsBean.getData().getId());
                        kingData.putData(DataKey.PRICE, orderDetailsBean.getData().getTotal_price());
                        kingData.putData(DataKey.TIME, orderDetailsBean.getData().getEnd_time());
                        kingData.sendBroadCast("ZZREFRESHPAY");
                        break;
                    case 2://货到付款
                        CallServer.Post(ActionKey.ORDER_COMPLETE + "WAIT", ActionKey.ORDER_COMPLETE, new OrderDetailsParam(orderDetailsBean.getData().getId()), BaseBean.class, OrderDetailsActivity.this);
                        break;
                    case 3://送货中
                        CallServer.Post(ActionKey.ORDER_COMPLETE + "FINISH", ActionKey.ORDER_COMPLETE, new OrderDetailsParam(orderDetailsBean.getData().getId()), BaseBean.class, OrderDetailsActivity.this);
                        break;
                    case 4://未送货

                        break;
                    case 5://未评价
                        intent = new Intent(OrderDetailsActivity.this, MineGoodsEvaluateActivity.class);
                        intent.putExtra("id", orderDetailsBean.getData().getId());
                        startActivity(intent);
                        overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);
                        break;
                    case 6://已取消

                        break;
                    case 7://已评价
                        intent = new Intent(OrderDetailsActivity.this, MineGoodsEvaluateActivity.class);
                        intent.putExtra("id", orderDetailsBean.getData().getId());
                        startActivity(intent);
                        overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);
                        break;
                }
                break;
            case R.id.ay_details_cancel_tv:
                switch (orderDetailsBean.getData().getStatus()) {
                    case 1://未付款
                        final CustomDialog.Builder ibuilder = new CustomDialog.Builder(mActivity);
                        ibuilder.setTitle("取消订单");
                        ibuilder.setMessage("你确定要取消订单吗？");
                        ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                CallServer.Post(ActionKey.CANCEL_ORDER+ "CANCEL", ActionKey.CANCEL_ORDER, new OrderDetailsParam(orderDetailsBean.getData().getId()), BaseBean.class, OrderDetailsActivity.this);

                            }
                        });
                        ibuilder.setNegativeButton("取消", null);
                        ibuilder.create().show();
                        break;
                    case 2://货到付款

                        break;
                    case 3://送货中

                        break;
                    case 4://未送货

                        break;
                    case 5://未评价

                        break;
                    case 6://已取消
                        final CustomDialog.Builder ibuilder1 = new CustomDialog.Builder(mActivity);
                        ibuilder1.setTitle("删除订单");
                        ibuilder1.setMessage("你确定要删除订单吗？");
                        ibuilder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                CallServer.Post(ActionKey.DEL_ORDER + "DELETE", ActionKey.DEL_ORDER, new OrderDetailsParam(orderDetailsBean.getData().getId()), BaseBean.class, OrderDetailsActivity.this);
                                dialogInterface.dismiss();
                            }
                        });
                        ibuilder1.setNegativeButton("取消", null);
                        ibuilder1.create().show();

                        break;
                    case 7:// 已评价
                        final CustomDialog.Builder ibuilder2 = new CustomDialog.Builder(mActivity);
                        ibuilder2.setTitle("删除订单");
                        ibuilder2.setMessage("你确定要删除订单吗？");
                        ibuilder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                CallServer.Post(ActionKey.DEL_ORDER + "SEVEN", ActionKey.DEL_ORDER, new OrderDetailsParam(orderDetailsBean.getData().getId()), BaseBean.class, OrderDetailsActivity.this);
                                dialogInterface.dismiss();
                            }
                        });
                        ibuilder2.setNegativeButton("取消", null);
                        ibuilder2.create().show();

                        break;

                }
                break;
        }
    }

    class DetailsGoodsAdapter extends KingAdapter {

        public DetailsGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            DetailsViewHolder viewHolder = (DetailsViewHolder) o;
            viewHolder.mNameTv.setText(orderDetailsBean.getData().getGoods().get(i).getTitle());
            viewHolder.mWeightTv.setText(orderDetailsBean.getData().getGoods().get(i).getSubtitle());
            viewHolder.mNumTv.setText("x" + orderDetailsBean.getData().getGoods().get(i).getNumber());
            viewHolder.mPriceTv.setText("￥" + orderDetailsBean.getData().getGoods().get(i).getPrice());
            Glide(orderDetailsBean.getData().getGoods().get(i).getImage(), viewHolder.mImgIv);
        }
    }

    class DetailsViewHolder {
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
