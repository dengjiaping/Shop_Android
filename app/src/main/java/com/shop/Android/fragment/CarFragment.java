package com.shop.Android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.king.KingApplication;
import com.king.Utils.FUtil;
import com.king.View.slidelistview.KingSlideAdapter;
import com.king.View.slidelistview.SlideBaseAdapter;
import com.king.View.slidelistview.SlideListView;
import com.king.View.slidelistview.wrap.SlideItemWrapLayout;
import com.shop.Android.BaseApplication;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.base.TestTwoAdapter;
import com.shop.Android.widget.AnimRefreshListView;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class CarFragment extends BaseFragment {

    private String TAG = "car";
    private AnimRefreshListView mListLv;

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
        if (adapter == null) {
            adapter = new CarAdapter(8, new CarViewHolder());
            mListLv.setAdapter(adapter);
        } else {
            adapter.setSize(8);
            mListLv.setAdapter(adapter);
        }
        mListLv.setListener(new AnimRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListLv.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListLv.onLoadComplete();
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onClickSet(int i) {

    }

    private CarAdapter adapter;

    class CarViewHolder {

    }

    class CarAdapter extends SlideBaseAdapter {
        private int size;
        private Object viewHolder;

        public void setSize(int size) {
            this.size = size;
        }

        public CarAdapter(int size, Object viewHolder) {
            super(CarFragment.this.mContext);
            this.size = size;
            this.viewHolder = viewHolder;
        }

        public SlideListView.SlideMode getSlideModeInPosition(int position) {
            if (position == 0 || position == 4) {
                return SlideListView.SlideMode.NONE;
            }
            return SlideListView.SlideMode.RIGHT;
        }

        public int getFrontViewId(int position) {
            if (position == 0 || position == 4) {
                return R.layout.item_car_title_list;
            }
            return R.layout.item_car_list;
        }

        public int getLeftBackViewId(int position) {
            return 0;
        }

        public int getRightBackViewId(int position) {
            return R.layout.slide_right_delete;
        }

        public int getCount() {
            return size;
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = createConvertView(i);
                FUtil.findViewInItem(viewHolder, view);
                view.setTag(viewHolder);
            } else {
                viewHolder = view.getTag();
            }
            //数据填充
            return view;
        }
    }


}
