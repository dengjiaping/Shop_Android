package com.shop.Android.fragment;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Base.KingData;
import com.king.Dialog.CustomDialog;
import com.king.Internet.user_interface.xCallback;
import com.king.Internet.user_method.CallServer;
import com.shop.Android.Config;
import com.shop.Android.DataKey;
import com.shop.Android.activity.LoginActivity;
import com.shop.Android.activity.MainActivity;
import com.shop.Android.activity.OrderDetailsActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.OrderBean;
import com.shop.Net.Param.Order;
import com.shop.Net.Param.OrderDetailsParam;
import com.shop.Net.Param.OrderWaitPayParam;
import com.shop.R;
import com.shop.wxapi.WXPayEntryActivity;

import java.util.List;

/**
 * Created by admin on 2016/9/26.
 */
public class OrderCancelFragment extends BaseFragment {
    private String TAG = "cancel";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mRelayoutRl;
    private CancelOrderAdapter cancelOrderAdapter;
    private CancelGoodsAdapter cancelGoodsAdapter;
    private OrderBean orderBean;
    private List<OrderBean.DataBean.GoodsBean> goodBeanCancel;
    private OrderBean.DataBean bean;


    @Override
    protected int loadLayout() {

        return R.layout.fragment_cancel_order;
    }

    @Override
    protected void init() {
        F();
        kingData.registerWatcher(Config.CANCEL_ORDER, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("4"), OrderBean.class);
            }
        });
        mListRv.setPullLoadEnable(false);
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("4"), OrderBean.class);
            }

            @Override
            public void onLoadMore() {
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("4"), OrderBean.class);
    }

    @Override
    public void onSuccess(String what, Object result) {
        mListRv.onRefreshComplete();
        mListRv.onLoadComplete();
        switch (what) {
            case ActionKey.ORDER_INDEX:
                orderBean = (OrderBean) result;
                if (MainActivity.index==2) {
                    if (orderBean.getCode() == 200) {
                        if (orderBean.getData() == null || orderBean.getData().size()==0) {
                            mRelayoutRl.setVisibility(View.VISIBLE);
                        } else {
                            mRelayoutRl.setVisibility(View.GONE);
                            try {
                                cancelOrderAdapter = new CancelOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new CancelViewHolder());
                                mListRv.setAdapter(cancelOrderAdapter);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    } else if (orderBean.getCode()==2001){
                        ToastInfo("请登录");
                        openActivity(LoginActivity.class);
                    }else {
                        ToastInfo(orderBean.getMsg());
                    }
                }
                break;


        }
    }



    @Override
    protected void onClickSet(int i) {

    }

    class CancelOrderAdapter extends KingAdapter {

        public CancelOrderAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            CancelViewHolder viewHolder = (CancelViewHolder) o;
            bean = orderBean.getData().get(i);
            goodBeanCancel = orderBean.getData().get(i).getGoods();
            viewHolder.mNameTv.setText(bean.getShop_name());
            viewHolder.mTotalTv.setText("￥" + bean.getTotal_price());
            viewHolder.mFeeTv.setText("配送费: ￥" + bean.getExpenses());
            viewHolder.mNumTv.setText("共 " + goodBeanCancel.size() + "件");
            try {
                cancelGoodsAdapter = new CancelGoodsAdapter(goodBeanCancel.size(), R.layout.item_order_goods, new CancelGoodsViewHolder());
                viewHolder.mListSv.setAdapter(cancelGoodsAdapter);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            viewHolder.mListSv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    kingData.putData(DataKey.ORDER, bean.getId());
                    kingData.sendBroadCast(Config.ORDER_ID);
                    openActivity(OrderDetailsActivity.class);
                }
            });
            switch (orderBean.getData().get(i).getStatus()) {
                case 6:
                    viewHolder.mTypeTv.setText("已取消");
                    viewHolder.mDelTv.setText("删除订单");
                    viewHolder.mPayTv.setText("重新购买");
                    viewHolder.mDelTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final CustomDialog.Builder ibuilder = new CustomDialog.Builder(mActivity);
                            ibuilder.setTitle("删除订单");
                            ibuilder.setMessage("你确定要删除订单吗？");
                            ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialogInterface, int i) {
                                    CallServer.Post(ActionKey.DEL_ORDER, ActionKey.DEL_ORDER, new OrderDetailsParam(bean.getId()), BaseBean.class, new xCallback() {
                                        @Override
                                        public void onSuccess(String s, Object o) {
                                            mListRv.onRefreshComplete();
                                            mListRv.onLoadComplete();
                                            BaseBean baseBean = (BaseBean) o;
                                            if (baseBean.getCode() == 200) {
                                                ToastInfo("删除成功");
                                                kingData.sendBroadCast(Config.CANCEL_ORDER);
                                                dialogInterface.dismiss();
                                            } else if (baseBean.getCode() == 2001) {
                                                ToastInfo("请登录");
                                                openActivity(LoginActivity.class);
                                            }
                                        }

                                        @Override
                                        public void onFinished(String s) {
                                            mListRv.onRefreshComplete();
                                            mListRv.onLoadComplete();
                                        }

                                        @Override
                                        public void onError(String s) {
                                            mListRv.onRefreshComplete();
                                            mListRv.onLoadComplete();
                                        }

                                        @Override
                                        public void onCancelled(String s) {

                                        }

                                        @Override
                                        public void onStart(String s) {

                                        }
                                    });


                                }
                            });
                            ibuilder.setNegativeButton("取消", null);
                            ibuilder.create().show();
                        }
                    });
                    viewHolder.mPayTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            kingData.putData(DataKey.ID, bean.getId());
                            kingData.putData(DataKey.PRICE,bean.getTotal_price());
                            kingData.putData(DataKey.TIME,bean.getEnd_time());
                            kingData.sendBroadCast("ZZREFRESHPAY");
                            openActivity(WXPayEntryActivity.class);
                        }
                    });
                    break;

            }
            viewHolder.mTimeTv.setVisibility(View.GONE);
        }
    }

    class CancelViewHolder {
        String TAG = "order";
        NoScrollListView mListSv;
        TextView mNameTv;
        TextView mTimeTv;
        TextView mTypeTv;
        TextView mTotalTv;
        TextView mNumTv;
        TextView mFeeTv;
        TextView mPayTv;
        TextView mDelTv;
    }

    class CancelGoodsAdapter extends KingAdapter {

        public CancelGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            try {
            OrderBean.DataBean.GoodsBean goodsBean = bean.getGoods().get(i);
            CancelGoodsViewHolder viewHolder = (CancelGoodsViewHolder) o;
            viewHolder.mNameTv.setText(goodsBean.getTitle());
            viewHolder.mNumTv.setText("x" + goodsBean.getNumber());
            viewHolder.mPriceTv.setText("￥" + goodsBean.getPrice());
            viewHolder.mWeightTv.setText(goodsBean.getSubtitle());

                Glide(goodsBean.getImage(), viewHolder.mImgIv);
            }catch (Exception px){
                px.printStackTrace();
            }

        }
    }

    class CancelGoodsViewHolder {
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
