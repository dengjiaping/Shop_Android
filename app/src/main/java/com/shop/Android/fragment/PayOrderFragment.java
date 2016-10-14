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
import com.shop.Android.Config;
import com.shop.Android.DataKey;
import com.shop.Android.activity.LoginActivity;
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

import java.util.List;

/**
 * Created by admin on 2016/9/26.
 */
public class PayOrderFragment extends BaseFragment {
    private String TAG = "pay";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mRelayoutRl;
    private PayOrderAdapter payOrderAdapter;
    private PayGoodsAdapter payGoodsAdapter;
    private int page = 0;
    private OrderBean orderBean;
    private List<OrderBean.DataBean.GoodsBean> goodBean;
    private OrderBean.DataBean bean;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_pay_order;
    }

    @Override
    protected void init() {
        F();
        Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("2", String.valueOf(page)), OrderBean.class);
        kingData.registerWatcher(Config.PAY_ORDER, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("2", String.valueOf(page)), OrderBean.class);
            }
        });
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("2", String.valueOf(page)), OrderBean.class);
            }

            @Override
            public void onLoadMore() {
                page++;
                Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("2", String.valueOf(page)), OrderBean.class);
            }
        });
    }

    @Override
    public void onSuccess(String what, Object result) {
        mListRv.onRefreshComplete();
        mListRv.onLoadComplete();
        switch (what) {
            case ActionKey.ORDER_INDEX:
                orderBean = (OrderBean) result;
                if (orderBean.getCode() == 200) {
                    try {
                        payOrderAdapter = new PayOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new PayViewHolder());
                        mListRv.setAdapter(payOrderAdapter);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }else {
                    ToastInfo(orderBean.getMsg());
                }
                break;
            case ActionKey.CANCEL_ORDER:
                BaseBean bean = (BaseBean) result;
                if (bean.getCode() == 200) {
                    ToastInfo("取消成功");
                } else if (bean.getCode() == 2001) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(bean.getMsg());
                }
                break;
            case ActionKey.ORDER_COMPLETE:
                BaseBean baseBean = (BaseBean) result;
                if (baseBean.getCode()==200){
                    ToastInfo("确认收货成功");
                }else if (baseBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(baseBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {

    }

    class PayOrderAdapter extends KingAdapter {

        public PayOrderAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            PayViewHolder viewHolder = (PayViewHolder) o;
            bean = orderBean.getData().get(i);
            goodBean = orderBean.getData().get(i).getGoods();
            payGoodsAdapter = new PayGoodsAdapter(goodBean.size(), R.layout.item_order_goods, new PayGoodsViewHolder());
            viewHolder.mListSv.setAdapter(payGoodsAdapter);
            viewHolder.mNameTv.setText(bean.getShop_name());
            viewHolder.mTotalTv.setText("￥" + bean.getTotal_price());
            viewHolder.mFeeTv.setText("配送费: ￥" + bean.getExpenses());
            viewHolder.mNumTv.setText("共 " + goodBean.size() + "件");
            switch (bean.getStatus()) {
                case 3:
                    viewHolder.mTypeTv.setText("送货中");
                    viewHolder.mDelTv.setText("确认收货");
                    viewHolder.mPayTv.setVisibility(View.VISIBLE);
                    viewHolder.mPayTv.setText("查看详情");
                    viewHolder.mPayTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            kingData.putData(DataKey.ORDER, bean.getId());
                            kingData.sendBroadCast(Config.ORDER_ID);
                            openActivity(OrderDetailsActivity.class);
                        }
                    });
                    viewHolder.mDelTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Post(ActionKey.ORDER_COMPLETE, new OrderDetailsParam(bean.getId()), BaseBean.class);
                            kingData.sendBroadCast(Config.PAY_ORDER);
                        }
                    });

                    break;
                case 4:
                    viewHolder.mTypeTv.setText("未送货");
                    viewHolder.mDelTv.setText("取消订单");
                    viewHolder.mPayTv.setVisibility(View.GONE);
                    viewHolder.mDelTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final CustomDialog.Builder ibuilder = new CustomDialog.Builder(mContext);
                            ibuilder.setTitle("取消订单");
                            ibuilder.setMessage("你确定要取消订单吗？");
                            ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Post(ActionKey.CANCEL_ORDER, new OrderDetailsParam(bean.getId()), BaseBean.class);
                                    dissLoadingDialog();
                                    kingData.sendBroadCast(Config.PAY_ORDER);
                                }
                            });
                            ibuilder.setNegativeButton("取消", null);
                            ibuilder.create().show();
                        }
                    });
                    break;
            }
            mListRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    kingData.putData(DataKey.ORDER,bean.getId());
                    kingData.sendBroadCast(Config.ORDER_ID);
                    openActivity(OrderDetailsActivity.class);
                }
            });
            viewHolder.mTimeTv.setVisibility(View.GONE);
        }
    }

    class PayViewHolder {
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

    class PayGoodsAdapter extends KingAdapter {

        public PayGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);

        }

        @Override
        public void padData(int i, Object o) {
            PayGoodsViewHolder viewHolder = (PayGoodsViewHolder) o;
            viewHolder.mNameTv.setText(goodBean.get(i).getTitle());
            viewHolder.mNumTv.setText("x" + goodBean.get(i).getNumber());
            viewHolder.mPriceTv.setText("￥" + goodBean.get(i).getPrice());
            viewHolder.mWeightTv.setText(goodBean.get(i).getSubtitle());
            Glide(goodBean.get(i).getImage(), viewHolder.mImgIv);
        }
    }

    class PayGoodsViewHolder {
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
