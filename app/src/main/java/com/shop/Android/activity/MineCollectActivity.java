package com.shop.Android.activity;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.View.slidelistview.KingSlideAdapter;
import com.king.View.slidelistview.SlideListView;
import com.king.View.slidelistview.wrap.SlideItemWrapLayout;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/20.
 */
public class MineCollectActivity extends BaseActvity {
    private String TAG = "collect";
    private AnimNoLineRefreshListView mListRv;
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
        mineCollectAdapter = new MineCollectAdapter(3,R.layout.activity_mine_collect_item,R.layout.slide_right_collect,new CollectViewHolder());
        mListRv.setAdapter(mineCollectAdapter);
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListRv.onRefreshComplete();
                    }
                },1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListRv.onLoadComplete();
                    }
                },1000);
            }
        });


    }

    @Override
    protected void onClickSet(int i) {

    }
    class MineCollectAdapter extends KingSlideAdapter{


        public MineCollectAdapter(int size, int layout, int rightLayout, Object viewHolder) {
            super(size, layout, 0, rightLayout, viewHolder);
        }

        @Override
        public void padData(int i, SlideItemWrapLayout slideItemWrapLayout, Object o) {

        }

        @Override
        public SlideListView.SlideMode padMode(int i) {
            return SlideListView.SlideMode.RIGHT;
        }
    }
    class CollectViewHolder{
     private String TAG = "collect";
        private ImageView mPhotoIv;
        private TextView mNameTv;
        private TextView mNumTv;
    }
}
