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
public class PayOrderFragment extends BaseFragment {
    private String TAG = "pay";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mRelayoutRl;
    private PayOrderAdapter payOrderAdapter;
    private PayGoodsAdapter payGoodsAdapter;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_pay_order;
    }

    @Override
    protected void init() {
        F();
        payOrderAdapter = new PayOrderAdapter(2,R.layout.fragment_order_item,new PayViewHolder());
        mListRv.setAdapter(payOrderAdapter);
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
            payGoodsAdapter = new PayGoodsAdapter(2,R.layout.item_order_goods,new PayGoodsViewHolder());
            viewHolder.mListSv.setAdapter(payGoodsAdapter);
            viewHolder.mTimeTv.setVisibility(View.GONE);
        }
    }

    class PayViewHolder{
        String TAG = "order";
        NoScrollListView mListSv;
        TextView mNameTv;
        TextView mTimeTv;
        TextView mTypeTv;
        TextView mTotalTv;
        TextView mNumTv;
        TextView mFeeTv;
    }
    class PayGoodsAdapter extends KingAdapter{

        public PayGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);

        }

        @Override
        public void padData(int i, Object o) {

        }
    }
    class PayGoodsViewHolder{
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
