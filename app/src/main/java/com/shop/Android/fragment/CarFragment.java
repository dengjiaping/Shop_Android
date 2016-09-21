package com.shop.Android.fragment;

import android.widget.ListView;

import com.shop.Android.base.BaseFragment;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.base.TestTwoAdapter;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class CarFragment extends BaseFragment {

    private String TAG = "car";
    private ListView mListLv;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_car;
    }

    @Override
    protected void initTitleBar() {
        initTitle("购物车");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
    }

    @Override
    protected void init() {
        F();
        mListLv.setAdapter(new TestTwoAdapter(8, R.layout.item_car_list, R.layout.item_car_title_list));
    }

    @Override
    protected void onClickSet(int i) {

    }
}
