package com.shop.Android.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/20.
 */
public class MineEvaluateActivity extends BaseActvity {
    private String TAG = "evaluate";
    private AnimNoLineRefreshListView mListRv;
    private MineEvaluateAdapter mineEvaluateAdapter;


    @Override
    protected int loadLayout() {
        return R.layout.activity_mine_evaluate;
    }

    @Override
    protected void initTitleBar() {
        initTitle("我的评价");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        mineEvaluateAdapter = new MineEvaluateAdapter(3,R.layout.activity_mine_evaluate_item,new EvaluateViewHolder());
        mListRv.setAdapter(mineEvaluateAdapter);

    }


    @Override
    protected void onClickSet(int i) {
        switch (i){

        }
    }

    class MineEvaluateAdapter extends KingAdapter {

        public MineEvaluateAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {

        }
    }
    class EvaluateViewHolder {
        private String TAG = "mine";
        private TextView mNumTv;
        private TextView mAllTv;
        private TextView mEvaluateTv;
        private TextView mTimeTv;
        private TextView mNameTv;
        private ImageView mGoodsIv;

    }
}
