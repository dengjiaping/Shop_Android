package com.xiaojishop.Android.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaojishop.Android.BaseApplication;

/**
 * Created by admin on 2016/9/21.
 */
public class TestTwoAdapter extends BaseAdapter {


    private int size;
    private int mLayoutId;
    private int mLayoutId2;

    public TestTwoAdapter(int size, int layoutId, int layoutId2) {
        this.size = size;
        this.mLayoutId = layoutId;
        this.mLayoutId2 = layoutId2;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (position == 0 || position == 4) {
            View view1 = View.inflate(BaseApplication.getAppContext(), mLayoutId2, null);
            return view1;
        } else {
            View view1 = View.inflate(BaseApplication.getAppContext(), mLayoutId, null);
            return view1;
        }
    }
}
