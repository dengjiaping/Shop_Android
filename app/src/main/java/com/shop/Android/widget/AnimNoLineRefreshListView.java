package com.shop.Android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.king.Utils.UIUtil;
import com.king.View.refreshview.XRefreshView;
import com.king.View.refreshview.listener.OnBottomLoadMoreTime;
import com.king.View.slidelistview.SlideListView;
import com.shop.R;


/**
 * Created by admin on 2016/8/15.
 */
public class AnimNoLineRefreshListView extends LinearLayout {

    private SlideListView mListNlv;


    public void setListener(onListener listener) {
        this.listener = listener;
    }

    private onListener listener;

    public AnimNoLineRefreshListView(Context context) {
        super(context, null, 0);
        init(context);
    }

    public AnimNoLineRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public AnimNoLineRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;

    private XRefreshView mRefreshXrv;
    private final int mPinnedTime = 0;

    private void init(Context context) {
        final View view = View.inflate(context, R.layout.refresh_anim_no_line_layout, this);
        mListNlv = (SlideListView) view.findViewById(R.id.refresh_list);

        mRefreshXrv = UIUtil.findViewById(view, R.id.refresh_xrv);
        // 设置是否可以下拉刷新
        mRefreshXrv.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        mRefreshXrv.setPullLoadEnable(true);
        mRefreshXrv.setPinnedTime(mPinnedTime);

        mRefreshXrv.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    listener.onRefresh();
                }
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                if (listener != null) {
                    listener.onLoadMore();
                }
            }
        });
    }

    public void setAdapter(BaseAdapter adapter) {
        mListNlv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(mListNlv);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListNlv.setOnItemClickListener(listener);
    }

    /**
     * scrollview嵌套listview
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    public interface onListener {
        void onRefresh();

        void onLoadMore();
    }

    public void startRefresh() {
        mRefreshXrv.scrollTo(0, 0);
        mRefreshXrv.startRefresh();
    }

    public void onRefreshComplete() {
        mRefreshXrv.stopRefresh();
    }

    public void onLoadComplete() {
        mRefreshXrv.stopLoadMore();
    }

    public void setPullRefreshEnable(boolean b) {
        if (mRefreshXrv != null) {
            mRefreshXrv.setPullRefreshEnable(b);
        }
    }


    public void setPullLoadEnable(boolean b) {
        if (mRefreshXrv != null) {
            mRefreshXrv.setPullLoadEnable(b);
        }
    }


}
