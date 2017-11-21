package com.xiaojishop.Android.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Android.widget.AnimNoLineRefreshListView;
import com.xiaojishop.Android.widget.NoScrollListView;
import com.xiaojishop.R;

/**
 * Created by admin on 2016/9/28.
 */
public class OrderSearchActivity extends BaseActvity {
    private String TAG = "search";
    private AnimNoLineRefreshListView mListAl;
    private TextView mContentTv;
    private OrderSearchAdapter orderSearchAdapter;
    private SearchGoodsAdapter searchGoodsAdapter;

    @Override
    protected int loadLayout() {
        return R.layout.activity_order_search;
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
        orderSearchAdapter = new OrderSearchAdapter(1, R.layout.fragment_order_item, new OrderSearchViewHolder());
        mListAl.setAdapter(orderSearchAdapter);
    }

    @Override
    protected void onClickSet(int i) {

    }

    class OrderSearchAdapter extends KingAdapter {

        public OrderSearchAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            OrderSearchViewHolder viewHolder = (OrderSearchViewHolder) o;
            searchGoodsAdapter = new SearchGoodsAdapter(1,R.layout.item_order_goods,new SearchGoodsViewHolder());
            viewHolder.mListSv.setAdapter(searchGoodsAdapter);
        }
    }

    class OrderSearchViewHolder {
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

    class SearchGoodsAdapter extends KingAdapter{

        public SearchGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {

        }
    }
   class SearchGoodsViewHolder{
       String TAG = "goods";
       ImageView mImgIv;
       TextView mNameTv;
       TextView mWeightTv;
       TextView mPriceTv;
       TextView mNumTv;
   }
}
