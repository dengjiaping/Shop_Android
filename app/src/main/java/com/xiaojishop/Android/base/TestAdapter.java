package com.xiaojishop.Android.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaojishop.Android.BaseApplication;


/**
 * Created by mao on 2016/7/25.
 */
public class TestAdapter extends BaseAdapter {


    private int size;
    private int mLayoutId;


    public TestAdapter(int size, int layoutId) {
        this.size = size;
        this.mLayoutId = layoutId;
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
         View  view1 = View.inflate(BaseApplication.getAppContext(), mLayoutId, null);
        return view1;
    }
}
