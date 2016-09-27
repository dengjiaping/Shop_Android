package com.shop.Android.activity;

import android.widget.ListView;

import com.shop.Android.base.BaseActvity;
import com.shop.Android.base.TestAdapter;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class EvalueActivity extends BaseActvity {

    private String TAG = "evalue";
    private ListView mListLv;

    @Override
    protected int loadLayout() {
        return R.layout.activity_evalue;
    }

    @Override
    protected void initTitleBar() {
        initTitle("评论");
    }

    @Override
    protected void init() {
        F();
        mListLv.setAdapter(new TestAdapter(5, R.layout.item_evalue_list));

    }

    @Override
    protected void onClickSet(int i) {

    }
}
