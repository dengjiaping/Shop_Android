package com.shop.Android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Base.KingData;
import com.shop.Android.Config;
import com.shop.Android.DataKey;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.TimeTextView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.OrderDetailsBean;
import com.shop.Net.Param.OrderDetailsParam;
import com.shop.R;
import com.shop.Utils.TimeUtils;

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
                Post(ActionKey.ORDER_DETAILS,new OrderDetailsParam(id), OrderDetailsBean.class);
            }
        });

        setOnClicks(mPayTv,mCancelTv);


    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.ORDER_DETAILS:
                orderDetailsBean = (OrderDetailsBean) result;
                if (orderDetailsBean.getCode()==200){
                    if (orderDetailsBean.getData()!=null){
                         switch (orderDetailsBean.getData().getStatus()){
                             case 1:
                                 mOrderTv.setText(orderDetailsBean.getData().getTotal_price()+"元");
                                 mWaitLl.setVisibility(View.VISIBLE);
                                 mDistributionRl.setVisibility(View.GONE);
                                 mUndistributionxRl.setVisibility(View.GONE);
                                 mFinishRl.setVisibility(View.GONE);
                                 mCancelRl.setVisibility(View.GONE);
                                 mTimesLl.setVisibility(View.VISIBLE);
                                 if (!orderDetailsBean.getData().getEnd_time().equals("")){
                                     int time = (int) (TimeUtils.date2unix2(orderDetailsBean.getData().getEnd_time()) - TimeUtils.now());
                                     int hour = time / 60 / 60;
                                     int min = (time - (hour * 60 * 60)) / 60;
                                     int sec = time - (hour * 60 * 60) - (min * 60);


                                     mTimesTv.setTimes(new long[]{hour, min, sec});
                                     if (!mTimesTv.isRun()) {
                                         mTimesTv.run();
                                     }
                                 }
                                 break;
                             case 2:
                                 mWaitLl.setVisibility(View.VISIBLE);
                                 mDistributionRl.setVisibility(View.GONE);
                                 mUndistributionxRl.setVisibility(View.GONE);
                                 mFinishRl.setVisibility(View.GONE);
                                 mCancelRl.setVisibility(View.GONE);
                                 mTimesLl.setVisibility(View.GONE);
                                 break;
                             case 3:
                                 mDistributionRl.setVisibility(View.VISIBLE);
                                 mWaitLl.setVisibility(View.GONE);
                                 mUndistributionxRl.setVisibility(View.GONE);
                                 mFinishRl.setVisibility(View.GONE);
                                 mCancelRl.setVisibility(View.GONE);
                                 mTimesLl.setVisibility(View.GONE);
                                 break;
                             case 4:
                                 mUndistributionxRl.setVisibility(View.VISIBLE);
                                 mDistributionRl.setVisibility(View.GONE);
                                 mWaitLl.setVisibility(View.GONE);
                                 mFinishRl.setVisibility(View.GONE);
                                 mCancelRl.setVisibility(View.GONE);
                                 mTimesLl.setVisibility(View.GONE);
                                 break;
                             case 5:
                                 mFinishRl.setVisibility(View.VISIBLE);
                                 mUndistributionxRl.setVisibility(View.GONE);
                                 mDistributionRl.setVisibility(View.GONE);
                                 mWaitLl.setVisibility(View.GONE);
                                 mCancelRl.setVisibility(View.GONE);
                                 mCancelTv.setVisibility(View.GONE);
                                 mPayTv.setText("评价商品");
                                 mTimesLl.setVisibility(View.GONE);
                                 break;
                             case 6:
                                 mCancelRl.setVisibility(View.VISIBLE);
                                 mFinishRl.setVisibility(View.GONE);
                                 mUndistributionxRl.setVisibility(View.GONE);
                                 mDistributionRl.setVisibility(View.GONE);
                                 mWaitLl.setVisibility(View.GONE);
                                 mTimesLl.setVisibility(View.GONE);
                                 break;
                             case 7:
                                 mFinishRl.setVisibility(View.VISIBLE);
                                 mUndistributionxRl.setVisibility(View.GONE);
                                 mDistributionRl.setVisibility(View.GONE);
                                 mWaitLl.setVisibility(View.GONE);
                                 mCancelRl.setVisibility(View.GONE);
                                 mCancelTv.setVisibility(View.GONE);
                                 mTimesLl.setVisibility(View.GONE);
                                 mPayTv.setText("评价商品");
                                 break;
                         }

                        mAddressTv.setText(orderDetailsBean.getData().getReceiver_address());
                        mPhoneTv.setText(orderDetailsBean.getData().getReceiver_phone());
                        mFeeTv.setText("￥"+orderDetailsBean.getData().getExpenses());
                        mNameTv.setText(orderDetailsBean.getData().getReceiver_contacts());
                        mNumTv.setText(orderDetailsBean.getData().getOrder_number());
                        mPriceTv.setText("￥"+orderDetailsBean.getData().getTotal_price());
                        mContentTv.setText(orderDetailsBean.getData().getRemark());
                        mTimeTv.setText(orderDetailsBean.getData().getCreated_time());
                        mSendTv.setText(orderDetailsBean.getData().getSend_time());
                        detailsGoodsAdapter = new DetailsGoodsAdapter(orderDetailsBean.getData().getGoods().size(), R.layout.item_order_goods, new DetailsViewHolder());
                        mListSv.setAdapter(detailsGoodsAdapter);


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
        Intent intent;
        switch (i) {
            case R.id.ay_details_pay_tv:
                switch (orderDetailsBean.getData().getStatus()){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        intent = new Intent(OrderDetailsActivity.this,MineGoodsEvaluateActivity.class);
                        intent.putExtra("id",orderDetailsBean.getData().getId());
                        startActivity(intent);
                        overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);
                        break;
                    case 6:
                        break;
                    case 7:
                        intent = new Intent(OrderDetailsActivity.this,MineGoodsEvaluateActivity.class);
                        intent.putExtra("id",orderDetailsBean.getData().getId());
                        startActivity(intent);
                        overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);
                        break;
                }
                break;
            case R.id.ay_details_cancel_tv:
                break;
        }
    }
    class DetailsGoodsAdapter extends KingAdapter{

        public DetailsGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            DetailsViewHolder viewHolder = (DetailsViewHolder) o;
            viewHolder.mNameTv.setText(orderDetailsBean.getData().getGoods().get(i).getTitle());
            viewHolder.mWeightTv.setText(orderDetailsBean.getData().getGoods().get(i).getSubtitle());
            viewHolder.mNumTv.setText("x"+orderDetailsBean.getData().getGoods().get(i).getNumber());
            viewHolder.mPriceTv.setText("￥"+orderDetailsBean.getData().getGoods().get(i).getPrice());
            Glide(orderDetailsBean.getData().getGoods().get(i).getImage(),viewHolder.mImgIv);
        }
    }
    class DetailsViewHolder{
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
