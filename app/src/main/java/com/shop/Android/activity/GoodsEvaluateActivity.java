package com.shop.Android.activity;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/27.
 */
public class GoodsEvaluateActivity extends BaseActvity {
    private String TAG = "evaluate";
    private AnimNoLineRefreshListView mListVn;
    private GoodsEvaluateAdapter goodsEvaluateAdapter;
    @Override
    protected int loadLayout() {
        return R.layout.activity_evaluate_goods;
    }

    @Override
    protected void initTitleBar() {
        initTitle("商品评价");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        goodsEvaluateAdapter = new GoodsEvaluateAdapter(3,R.layout.activiy_evaluate_goods_item,new GoodsEvaluateViewHolder());
        mListVn.setAdapter(goodsEvaluateAdapter);

    }

    @Override
    protected void onClickSet(int i) {

    }

    class GoodsEvaluateAdapter extends KingAdapter{

        public GoodsEvaluateAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            GoodsEvaluateViewHolder viewHolder = (GoodsEvaluateViewHolder) o;
        }
    }

    class  GoodsEvaluateViewHolder{
        String TAG = "evaluate";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mPriceTv;
        TextView mContentTv;
        RadioButton mPraiseRb;
        RadioButton mMiddleRb;
        RadioButton mBadRb;
        EditText mFeelEt;
        TextView mNumTv;
        TextView mSubmitTv;
    }
}
