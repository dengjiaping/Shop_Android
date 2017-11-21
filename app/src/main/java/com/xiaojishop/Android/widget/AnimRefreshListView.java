package com.xiaojishop.Android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.king.Utils.UIUtil;
import com.king.View.refreshview.XRefreshView;
import com.king.View.slidelistview.SlideListView;
import com.xiaojishop.R;


/**
 * Created by admin on 2016/8/15.
 */
public class AnimRefreshListView extends LinearLayout {

    private SlideListView mListNlv;


    public void setListener(onListener listener) {
        this.listener = listener;
    }

    private onListener listener;

    public AnimRefreshListView(Context context) {
        super(context, null, 0);
        init(context);
    }

    public AnimRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public AnimRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;

    private XRefreshView mRefreshXrv;

    private void init(Context context) {
        final View view = View.inflate(context, R.layout.refresh_anim_layout, this);
        mListNlv = (SlideListView) view.findViewById(R.id.refresh_list);

        mRefreshXrv = UIUtil.findViewById(view, R.id.refresh_xrv);

        // 设置是否可以下拉刷新
        mRefreshXrv.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        mRefreshXrv.setPullLoadEnable(true);
        mRefreshXrv.setPinnedTime(0);
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
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListNlv.setOnItemClickListener(listener);
    }




    public interface onListener {
        void onRefresh();

        void onLoadMore();
    }

    public void startRefresh() {
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
