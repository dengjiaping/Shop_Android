package com.shop.Android.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class OrderCancelFragment extends BaseFragment {
    private String TAG = "cancel";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mRelayoutRl;
    private CancelOrderAdapter cancelOrderAdapter;
    private CancelGoodsAdapter cancelGoodsAdapter;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_cancel_order;
    }

    @Override
    protected void init() {
        F();
        cancelOrderAdapter = new CancelOrderAdapter(3,R.layout.fragment_order_item,new CancelViewHolder());
        mListRv.setAdapter(cancelOrderAdapter);
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
            cancelGoodsAdapter = new CancelGoodsAdapter(2,R.layout.item_order_goods,new CancelGoodsViewHolder());
            viewHolder.mListSv.setAdapter(cancelGoodsAdapter);
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
