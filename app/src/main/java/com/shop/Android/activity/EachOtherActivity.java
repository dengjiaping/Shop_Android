package com.shop.Android.activity;

import android.os.Handler;

import com.king.View.slidelistview.KingSlideAdapter;
import com.king.View.slidelistview.SlideListView;
import com.king.View.slidelistview.wrap.SlideItemWrapLayout;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.AnimRefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class EachOtherActivity extends BaseActvity {

    private String TAG = "eachother";
    private AnimNoLineRefreshListView mListLv;

    @Override
    protected int loadLayout() {
        return R.layout.activity_eachother;
    }

    @Override
    protected void initTitleBar() {
        initTitle("小方互动");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        if (adapter == null) {
            adapter = new EachAdapter(10, R.layout.item_eachother_list, new EachViewHolder());
            mListLv.setAdapter(adapter);
        } else {
            adapter.setSize(10);
            mListLv.setAdapter(adapter);
        }

        mListLv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListLv.onRefreshComplete();
                    }
                },1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListLv.onLoadComplete();
                    }
                },1000);
            }
        });
    }

    @Override
    protected void onClickSet(int i) {

    }

    private EachAdapter adapter;

    class EachViewHolder {

    }

    class EachAdapter extends KingSlideAdapter {

        public EachAdapter(int size, int layout, Object viewHolder) {
            super(size, layout, 0, 0, viewHolder);
        }

        @Override
        public void padData(int i, SlideItemWrapLayout slideItemWrapLayout, Object o) {

        }

        @Override
        public SlideListView.SlideMode padMode(int i) {
            return SlideListView.SlideMode.NONE;
        }
    }
}
