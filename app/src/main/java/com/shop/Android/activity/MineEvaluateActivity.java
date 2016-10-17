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
public class MineEvaluateActivity extends BaseActvity {
    private String TAG = "evaluate";
    private AnimNoLineRefreshListView mListRv;
    private GoodsEvaluateAdapter goodsEvaluateAdapter;


    @Override
    protected int loadLayout() {
        return R.layout.activity_mine_evaluate;
    }

    @Override
    protected void initTitleBar() {
        initTitle("我的评价");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        goodsEvaluateAdapter = new GoodsEvaluateAdapter(3,R.layout.activity_mine_evaluate_item,new GoodsEvaluateViewHolder());
        mListRv.setAdapter(goodsEvaluateAdapter);

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
        String TAG = "mine";
        ImageView mGoodsIv;
        TextView mNameTv;
        TextView mNumberTv;
        TextView mAllTv;
        TextView mEvaluateTv;
        TextView mTimeTv;
    }
}
