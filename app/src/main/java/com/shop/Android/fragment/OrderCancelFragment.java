package com.shop.Android.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.activity.LoginActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.OrderBean;
import com.shop.Net.Param.OrderWaitPayParam;
import com.shop.R;

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
    private List<OrderBean.DataBean.GoodsBean> goodsBeen;
    private int page = 0;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_cancel_order;
    }

    @Override
    protected void init() {
        F();
        Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("4", String.valueOf(page)), OrderBean.class);
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("4", String.valueOf(page)), OrderBean.class);
            }

            @Override
            public void onLoadMore() {
                page++;
                Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("4", String.valueOf(page)), OrderBean.class);
            }
        });
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.ORDER_INDEX:
                orderBean = (OrderBean) result;
                if (orderBean.getCode() == 200) {
                    if (orderBean.getData() == null) {
                        mRelayoutRl.setVisibility(View.VISIBLE);
                    } else {
                        mRelayoutRl.setVisibility(View.GONE);
                        cancelOrderAdapter = new CancelOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new CancelViewHolder());
                        mListRv.setAdapter(cancelOrderAdapter);
                    }
                } else if (orderBean.getCode() == 2001) {
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

    class CancelOrderAdapter extends KingAdapter {

        public CancelOrderAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            CancelViewHolder viewHolder = (CancelViewHolder) o;
            goodsBeen = orderBean.getData().get(i).getGoods();
            cancelGoodsAdapter = new CancelGoodsAdapter(goodsBeen.size(), R.layout.item_order_goods, new CancelGoodsViewHolder());
            viewHolder.mListSv.setAdapter(cancelGoodsAdapter);
            viewHolder.mNameTv.setText(orderBean.getData().get(i).getShop_name());
            viewHolder.mTotalTv.setText("￥" + orderBean.getData().get(i).getTotal_price());
            viewHolder.mFeeTv.setText("配送费: ￥" + orderBean.getData().get(i).getExpenses());
            viewHolder.mNumTv.setText("共 " + goodsBeen.size() + "件");
            switch (orderBean.getData().get(i).getStatus()) {
                case 6:
                    viewHolder.mTypeTv.setText("已取消");

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
    }

    class CancelGoodsAdapter extends KingAdapter {

        public CancelGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            CancelGoodsViewHolder viewHolder = (CancelGoodsViewHolder) o;
            viewHolder.mNameTv.setText(goodsBeen.get(i).getSubtitle());
            viewHolder.mNumTv.setText("x" + goodsBeen.get(i).getNumber());
            viewHolder.mPriceTv.setText("￥" + goodsBeen.get(i).getPrice());
            viewHolder.mWeightTv.setText(goodsBeen.get(i).getTitle());
            Glide(goodsBeen.get(i).getImage(), viewHolder.mImgIv);
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
