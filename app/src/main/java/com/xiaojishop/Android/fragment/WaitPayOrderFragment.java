package com.xiaojishop.Android.fragment;


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
import com.xiaojishop.Android.Config;
import com.xiaojishop.Android.DataKey;
import com.xiaojishop.Android.activity.LoginActivity;
import com.xiaojishop.Android.activity.OrderDetailsActivity;
import com.xiaojishop.Android.base.BaseFragment;
import com.xiaojishop.Android.widget.AnimNoLineRefreshListView;
import com.xiaojishop.Android.widget.NoScrollListView;
import com.xiaojishop.Android.widget.TimeTextView;
import com.xiaojishop.Net.ActionKey;
import com.xiaojishop.Net.Bean.BaseBean;
import com.xiaojishop.Net.Bean.OrderBean;
import com.xiaojishop.Net.Param.OrderDetailsParam;
import com.xiaojishop.Net.Param.OrderWaitPayParam;
import com.xiaojishop.R;
import com.xiaojishop.Utils.TimeUtils;
import com.xiaojishop.wxapi.WXPayEntryActivity;

import java.util.List;

/**
 * Created by admin on 2016/9/26.
 */
public class WaitPayOrderFragment extends BaseFragment {
    private String TAG = "wait";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mRelayoutRl;
    private WaitPayOrderAdapter waitPayOrderAdapter;
    private GoodsAdapter goodsAdapter;
    private OrderBean orderBean;
    private boolean isFirst = true;
    private List<OrderBean.DataBean.GoodsBean> goodsBean;

    @Override
    protected int loadLayout() {
        kingData.registerWatcher(Config.ORDER, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                CallServer.Post(ActionKey.ORDER_INDEX + "DATA", ActionKey.ORDER_INDEX, new OrderWaitPayParam("1"), OrderBean.class, WaitPayOrderFragment.this);
                mListRv.setAdapter(waitPayOrderAdapter);

            }
        });
        return R.layout.fragment_wait_pay_order;

    }

    @Override
    protected void init() {
        F();
        mListRv.setPullLoadEnable(false);
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                CallServer.Post(ActionKey.ORDER_INDEX + "REFRESH", ActionKey.ORDER_INDEX, new OrderWaitPayParam("1"), OrderBean.class, WaitPayOrderFragment.this);
            }

            @Override
            public void onLoadMore() {
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst) {
            Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("1"), OrderBean.class);
            isFirst = false;
        } else {

        }

    }

    @Override
    public void onSuccess(String what, Object result) {
        mListRv.onLoadComplete();
        mListRv.onRefreshComplete();
        switch (what) {
            case ActionKey.ORDER_INDEX:
                orderBean = (OrderBean) result;
                if (orderBean.getCode() == 200) {
                    if (orderBean.getData() == null || orderBean.getData().size() == 0) {
                        mRelayoutRl.setVisibility(View.VISIBLE);
                    } else {
                        mRelayoutRl.setVisibility(View.GONE);
                        waitPayOrderAdapter = new WaitPayOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new WaitPayViewHolder());
                        mListRv.setAdapter(waitPayOrderAdapter);
                    }
                } else {
                    ToastInfo(orderBean.getMsg());
                }
                break;
            case ActionKey.ORDER_INDEX + "DATA":
                orderBean = (OrderBean) result;
                if (orderBean.getCode() == 200) {
                    if (orderBean.getData() == null || orderBean.getData().size() == 0) {
                        mRelayoutRl.setVisibility(View.VISIBLE);
                        mListRv.setAdapter(null);
                    } else {
                        mRelayoutRl.setVisibility(View.GONE);
                        waitPayOrderAdapter = new WaitPayOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new WaitPayViewHolder());
                        mListRv.setAdapter(waitPayOrderAdapter);
                    }
                } else if (2001 == orderBean.getCode()) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(orderBean.getMsg());
                }
                break;
            case ActionKey.ORDER_INDEX + "REFRESH":
                orderBean = (OrderBean) result;
                if (orderBean.getCode() == 200) {
                    if (orderBean.getData() == null || orderBean.getData().size() == 0) {
                        mRelayoutRl.setVisibility(View.VISIBLE);
                    } else {
                        mRelayoutRl.setVisibility(View.GONE);
                        waitPayOrderAdapter = new WaitPayOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new WaitPayViewHolder());
                        mListRv.setAdapter(waitPayOrderAdapter);
                    }
                } else if (2001 == orderBean.getCode()) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(orderBean.getMsg());
                }
                break;


        }
    }

    @Override
    protected void onClickSet(int i) {

    }

    class WaitPayOrderAdapter extends KingAdapter {

        public WaitPayOrderAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);

        }

        @Override
        public void padData(int i, Object o) {
            WaitPayViewHolder viewHolder = (WaitPayViewHolder) o;
            final OrderBean.DataBean bean = orderBean.getData().get(i);
            try {
                goodsBean = orderBean.getData().get(i).getGoods();
                goodsAdapter = new GoodsAdapter(goodsBean.size(), R.layout.item_order_goods, new GoodsViewHolder());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            viewHolder.mListSv.setAdapter(goodsAdapter);
            viewHolder.mNameTv.setText(bean.getShop_name());
            viewHolder.mTotalTv.setText("￥" + bean.getTotal_price());
            viewHolder.mFeeTv.setText("配送费: ￥" + bean.getExpenses());
            viewHolder.mNumTv.setText("共 " + goodsBean.size() + "件");
            switch (orderBean.getData().get(i).getStatus()) {
                case 1:
                    viewHolder.mTypeTv.setText("未付款");
                    viewHolder.mDelTv.setText("取消订单");
                    if (!bean.getEnd_time().equals("")) {
                        int time = (int) (TimeUtils.date2unix2(bean.getEnd_time()) - TimeUtils.now());
                        int hour = time / 60 / 60;
                        int min = (time - (hour * 60 * 60)) / 60;
                        int sec = time - (hour * 60 * 60) - (min * 60);


                        viewHolder.mTimeTv.setTimes(new long[]{hour, min, sec});
                        if (!viewHolder.mTimeTv.isRun()) {
                            viewHolder.mTimeTv.run();
                        }
                    }
                    break;
                case 2:
                    viewHolder.mTypeTv.setText("货到付款");
                    viewHolder.mTimeTv.setVisibility(View.GONE);
                    break;
            }
            viewHolder.mPayTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kingData.putData(DataKey.ID, bean.getId());
                    kingData.putData(DataKey.PRICE, bean.getTotal_price());
                    kingData.putData(DataKey.TIME, bean.getEnd_time());
                    kingData.sendBroadCast("ZZREFRESHPAY");
                    openActivity(WXPayEntryActivity.class);
                }
            });
            viewHolder.mDelTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CustomDialog.Builder ibuilder = new CustomDialog.Builder(mActivity);
                    ibuilder.setTitle("取消订单");
                    ibuilder.setMessage("你确定要取消订单吗？");
                    ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, final int i) {
                            CallServer.Post(ActionKey.CANCEL_ORDER, ActionKey.CANCEL_ORDER, new OrderDetailsParam(bean.getId()), BaseBean.class, new xCallback() {
                                @Override
                                public void onSuccess(String s, Object o) {
                                    mListRv.onRefreshComplete();
                                    BaseBean bean = (BaseBean) o;
                                    if (bean.getCode() == 200) {
                                        ToastInfo("取消成功");
                                        dialogInterface.dismiss();
                                        kingData.sendBroadCast(Config.ORDER);
                                    } else if (bean.getCode() == 2001) {
                                        ToastInfo("请登录");
                                        openActivity(LoginActivity.class);
                                    } else {
                                        ToastInfo(bean.getMsg());
                                    }
                                }

                                @Override
                                public void onFinished(String s) {
                                    mListRv.onRefreshComplete();
                                }

                                @Override
                                public void onError(String s) {
                                    mListRv.onRefreshComplete();
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
            viewHolder.mListSv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    kingData.putData(DataKey.ORDER, bean.getId());
                    kingData.sendBroadCast(Config.ORDER_ID);
                    openActivity(OrderDetailsActivity.class);

                }
            });
        }
    }

    class WaitPayViewHolder {
        String TAG = "order";
        NoScrollListView mListSv;
        TextView mNameTv;
        TimeTextView mTimeTv;
        TextView mTypeTv;
        TextView mTotalTv;
        TextView mNumTv;
        TextView mFeeTv;
        TextView mPayTv;
        TextView mDelTv;


    }

    class GoodsAdapter extends KingAdapter {

        public GoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            GoodsViewHolder viewHolder = (GoodsViewHolder) o;
            try {
                viewHolder.mNameTv.setText(goodsBean.get(i).getSubtitle());
                viewHolder.mNumTv.setText("x" + goodsBean.get(i).getNumber());
                viewHolder.mPriceTv.setText("￥" + goodsBean.get(i).getPrice());
                viewHolder.mWeightTv.setText(goodsBean.get(i).getTitle());
                Glide(goodsBean.get(i).getImage(), viewHolder.mImgIv);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    class GoodsViewHolder {
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
