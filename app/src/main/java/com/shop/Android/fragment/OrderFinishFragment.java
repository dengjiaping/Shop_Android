package com.shop.Android.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class OrderFinishFragment extends BaseFragment {
    private String TAG = "finish";
    private RefreshListView mListRv;
    private FinishOrderAdapter finishOrderAdapter;
    private FinishGoodsAdapter finishGoodsAdapter;
    @Override
    protected int loadLayout() {
        return R.layout.fragment_finish_order;
    }

    @Override
    protected void init() {
        F();
        finishOrderAdapter = new FinishOrderAdapter(3,R.layout.fragment_order_item,new FinishViewHolder());
        mListRv.setAdapter(finishOrderAdapter);
    }

    @Override
    protected void onClickSet(int i) {

    }
    class FinishOrderAdapter extends KingAdapter{

        public FinishOrderAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            FinishViewHolder viewHolder = (FinishViewHolder) o;
            finishGoodsAdapter = new FinishGoodsAdapter(2,R.layout.item_order_goods,new FinishGoodsViewHolder());
            viewHolder.mListSv.setAdapter(finishGoodsAdapter);
            viewHolder.mTimeTv.setVisibility(View.GONE);
        }
    }
    class FinishViewHolder{
        String TAG = "order";
        NoScrollListView mListSv;
        TextView mNameTv;
        TextView mTimeTv;
        TextView mTypeTv;
        TextView mTotalTv;
        TextView mNumTv;
        TextView mFeeTv;
    }
    class FinishGoodsAdapter extends KingAdapter{

        public FinishGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {

        }
    }
    class FinishGoodsViewHolder{
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
