package com.shop.Android.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.king.View.gradationscroll.GradationScrollView;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.PlayViewPager;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class IndexFragment extends BaseFragment {

    private String TAG = "index";
    private GradationScrollView mGradutionGsv;
    private PlayViewPager mPlayviewPvp;
    private LinearLayout mTitleLl;
    private RecyclerView mRecycleRv;
    private NoScrollListView mListNlv;

    private String[] urls = new String[]{
            "http://img12.360buyimg.com/da/jfs/t2695/3/4114524600/99151/7fca55b2/57ad8e86N314092f5.webp",
            "http://img12.360buyimg.com/da/jfs/t2695/3/4114524600/99151/7fca55b2/57ad8e86N314092f5.webp",
            "http://img30.360buyimg.com/da/jfs/t3199/95/2309082525/55586/9688d644/57dfb2feNcb45c27e.jpg"
    };


    @Override
    protected int loadLayout() {
        return R.layout.fragment_index;
    }

    private int height;

    @Override
    protected void init() {
        F();
        mPlayviewPvp.setUrls(urls);
        addTitleSlideChange();
        mListNlv.setAdapter(new TestAdapter(5, R.layout.item_index_list));


    }

    private void addTitleSlideChange() {
        ViewTreeObserver vto = mPlayviewPvp.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTitleLl.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = mPlayviewPvp.getHeight();

                mGradutionGsv.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(GradationScrollView gradationScrollView, int i, int i1, int i2, int i3) {
                        if (i1 <= 0) {   //设置标题的背景颜色
                            mTitleLl.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
                        } else if (i1 > 0 && i1 <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            float scale = (float) i1 / height;
                            float alpha = (255 * scale);
                            mTitleLl.setBackgroundColor(Color.argb((int) alpha, 0xEA, 0x59, 0x3A));
                        } else {    //滑动到banner下面设置普通颜色
                            mTitleLl.setBackgroundColor(Color.argb((int) 255, 0xEA, 0x59, 0x3A));
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onClickSet(int i) {

    }

}
