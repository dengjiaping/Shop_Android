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
    private boolean isFirst = true;
    private List<OrderBean.DataBean.GoodsBean> goodBean;

    @Override
    protected int loadLayout() {
        kingData.registerWatcher(Config.PAY_ORDER, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                CallServer.Post(ActionKey.ORDER_INDEX+"DATA",ActionKey.ORDER_INDEX, new OrderWaitPayParam("2"), OrderBean.class,PayOrderFragment.this);
            }
        });
        return R.layout.fragment_pay_order;
    }

    @Override
    protected void init() {
        F();

        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                CallServer.Post(ActionKey.ORDER_INDEX+"REFRESH",ActionKey.ORDER_INDEX, new OrderWaitPayParam("2"), OrderBean.class,PayOrderFragment.this);
            }
            @Override
            public void onLoadMore() {
            }
        });
        mListRv.setPullLoadEnable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst){
            Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("2"), OrderBean.class);
            isFirst=false;
        }else {

        }
    }
    @Override
    public void onSuccess(String what, Object result) {
        mListRv.onRefreshComplete();
        mListRv.onLoadComplete();
        switch (what) {
            case ActionKey.ORDER_INDEX:
                orderBean = (OrderBean) result;
                    if (orderBean.getCode() == 200) {
                        if (orderBean.getData()==null || orderBean.getData().size()==0){
                            mRelayoutRl.setVisibility(View.VISIBLE);
                        }else {
                            mRelayoutRl.setVisibility(View.GONE);
                            payOrderAdapter = new PayOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new PayViewHolder());
                            mListRv.setAdapter(payOrderAdapter);
                        }

                    } else {
                        ToastInfo(orderBean.getMsg());
                    }
                break;

            case ActionKey.ORDER_COMPLETE:
                BaseBean baseBean = (BaseBean) result;
                if (baseBean.getCode() == 200) {
                    ToastInfo("确认收货成功");
                    kingData.sendBroadCast(Config.PAY_ORDER);
                } else if (baseBean.getCode() == 2001) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(baseBean.getMsg());
                }
                break;
            case ActionKey.ORDER_INDEX+"DATA":
                orderBean = (OrderBean) result;
                if (orderBean.getCode() == 200) {
                    if (orderBean.getData()==null || orderBean.getData().size()==0){
                        mRelayoutRl.setVisibility(View.VISIBLE);
                    }else {
                        mRelayoutRl.setVisibility(View.GONE);
                        payOrderAdapter = new PayOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new PayViewHolder());
                        mListRv.setAdapter(payOrderAdapter);
                    }

                } else if (2001==orderBean.getCode()){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(orderBean.getMsg());
                }
                break;
            case ActionKey.ORDER_INDEX+"REFRESH":
                orderBean = (OrderBean) result;
                if (orderBean.getCode() == 200) {
                    if (orderBean.getData()==null || orderBean.getData().size()==0){
                        mRelayoutRl.setVisibility(View.VISIBLE);
                    }else {
                        mRelayoutRl.setVisibility(View.GONE);
                        payOrderAdapter = new PayOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new PayViewHolder());
                        mListRv.setAdapter(payOrderAdapter);
                    }

                } else if (2001==orderBean.getCode()){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(orderBean.getMsg());
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
            final OrderBean.DataBean bean = orderBean.getData().get(i);
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
                    viewHolder.mPayTv.setText("查看详情");
                    viewHolder.mPayTv.setVisibility(View.VISIBLE);
                    viewHolder.mDelTv.setVisibility(View.GONE);
                    viewHolder.mPayTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            kingData.putData(DataKey.ORDER, bean.getId());
                            kingData.sendBroadCast(Config.ORDER_ID);
                            openActivity(OrderDetailsActivity.class);
                        }
                    });
                    break;
            }
            viewHolder.mListSv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    kingData.putData(DataKey.ORDER, bean.getId());
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
            try {
                viewHolder.mNameTv.setText(goodBean.get(i).getTitle());
                viewHolder.mNumTv.setText("x" + goodBean.get(i).getNumber());
                viewHolder.mPriceTv.setText("￥" + goodBean.get(i).getPrice());
                viewHolder.mWeightTv.setText(goodBean.get(i).getSubtitle());
                Glide(goodBean.get(i).getImage(), viewHolder.mImgIv);
            }catch (Exception ex){
                ex.printStackTrace();
            }

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
