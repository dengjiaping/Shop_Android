package com.shop.Android.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;

import com.king.Base.KingActivity;
import com.king.FlycoTableLayout.CommonTabLayout;
import com.king.FlycoTableLayout.SlidingTabLayout;
import com.shop.Android.fragment.IndexFragment;
import com.shop.Android.fragment.MineFragment;
import com.shop.Android.fragment.StorageFragment;
import com.shop.Android.fragment.TimeSpecialFragment;
import com.shop.R;

import java.util.ArrayList;

/**
 * Created by admin on 2016/9/26.
 */
public class SpecialSellActivity extends KingActivity {


    private String TAG = "special";
    private SlidingTabLayout mTabStl;
    private ViewPager mContentVp;

    @Override
    protected int loadLayout() {
        return R.layout.activity_special;
    }

    @Override
    protected void initTitleBar() {
        initTitle("今日特卖");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    private String[] titles = new String[]{
            "时间特卖", "库存特卖"
    };

    @Override
    protected void init() {
        F();
        mContentVp.setAdapter(new MyFragmentPagerAdapter(fragmentManager));
        mTabStl.setViewPager(mContentVp, titles);

    }

    @Override
    protected void onClickSet(int i) {

    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TimeSpecialFragment();
            } else {
                return new StorageFragment();
            }
        }

    }


}
