package com.shop.Android.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.activity.OrderDetailsActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class WaitPayOrderFragment extends BaseFragment {
    private String TAG = "wait";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mRelayoutRl;
    private WaitPayOrderAdapter waitPayOrderAdapter;
    private GoodsAdapter goodsAdapter;
    @Override
    protected int loadLayout() {
        return R.layout.fragment_wait_pay_order;
    }

    @Override
    protected void init() {
        F();
        waitPayOrderAdapter = new WaitPayOrderAdapter(3,R.layout.fragment_order_item,new WaitPayViewHolder());
        mListRv.setAdapter(waitPayOrderAdapter);
        mListRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openActivity(OrderDetailsActivity.class);
            }
        });

    }

    @Override
    protected void onClickSet(int i) {

    }

    class WaitPayOrderAdapter extends KingAdapter{

        public WaitPayOrderAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);

        }

        @Override
        public void padData(int i, Object o) {
            WaitPayViewHolder viewHolder = (WaitPayViewHolder) o;
            goodsAdapter = new GoodsAdapter(3,R.layout.item_order_goods,new GoodsViewHolder());
            viewHolder.mListSv.setAdapter(goodsAdapter);
            viewHolder.mPayTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openActivity(OrderDetailsActivity.class);
                }
            });

        }
    }
    class WaitPayViewHolder{
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
    class GoodsAdapter extends KingAdapter{

        public GoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {

        }
    }
    class GoodsViewHolder{
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
