package com.shop.Android.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.RefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/20.
 */
public class MineCollectActivity extends BaseActvity {
    private String TAG = "collect";
    private RefreshListView mListRv;
    private MineCollectAdapter mineCollectAdapter;
    @Override
    protected int loadLayout() {
        return R.layout.activity_mine_collect;
    }

    @Override
    protected void initTitleBar() {
        initTitle("我的收藏");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        mineCollectAdapter = new MineCollectAdapter(3,R.layout.activity_mine_collect_item,new CollectViewHolder());
        mListRv.setAdapter(mineCollectAdapter);



    }

    @Override
    protected void onClickSet(int i) {

    }
    class MineCollectAdapter extends KingAdapter{

        public MineCollectAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {

        }
    }
    class CollectViewHolder{
     private String TAG = "collect";
        private ImageView mPhotoIv;
        private TextView mNameTv;
        private TextView mNumTv;
    }
}
