package com.shop.Android.fragment;

import android.widget.ListView;

import com.king.Base.KingFragment;
import com.shop.Android.base.TestAdapter;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class TimeSpecialFragment extends KingFragment {

    private String TAG = "time";
    private ListView mListLv;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_time;
    }

    @Override
    protected void init() {
        F();
        mListLv.setAdapter(new TestAdapter(10,R.layout.item_time_list));

    }

    @Override
    protected void onClickSet(int i) {

    }
}
