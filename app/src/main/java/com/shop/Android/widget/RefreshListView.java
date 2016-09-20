package com.shop.Android.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.shop.R;


/**
 * Created by admin on 2016/8/15.
 */
public class RefreshListView extends LinearLayout {

    private NoScrollListView mListNlv;

    public SwipeRefreshLayout getmRefreshTop() {
        return mRefreshTop;
    }

    private SwipeRefreshLayout mRefreshTop;
    private ScrollView mSlideSv;
    private RelativeLayout mLoadRl;
    private OnLoadData loadData;

    public RefreshListView(Context context) {
        super(context, null, 0);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        final View view = View.inflate(context, R.layout.refresh_layout, this);
        mListNlv = (NoScrollListView) view.findViewById(R.id.refresh_list);
        mRefreshTop = (SwipeRefreshLayout) view.findViewById(R.id.refresh_top);
        mRefreshTop.setColorSchemeResources(R.color.my_color);
        mSlideSv = (ScrollView) view.findViewById(R.id.slide_sv);
        if (mSlideSv != null) {
            mSlideSv.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (mRefreshTop != null) {
                        mRefreshTop.setEnabled(mSlideSv.getScrollY() == 0);
                    }
                }
            });
        }
        mLoadRl = (RelativeLayout) view.findViewById(R.id.load_data);
        mSlideSv.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int scrollY = mSlideSv.getScrollY();
                        int height = view.getHeight();
                        int scrollViewMeasuredHeight = mSlideSv.getChildAt(0).getMeasuredHeight();
                        if (scrollY == 0) {
//                            System.out.println("滑动到了顶端 view.getScrollY()=" + scrollY);
                        }
                        if ((scrollY + height) == scrollViewMeasuredHeight) {
//                            System.out.println("滑动到了底部 scrollY=" + scrollY);
//                            System.out.println("滑动到了底部 height=" + height);
//                            System.out.println("滑动到了底部 scrollViewMeasuredHeight=" + scrollViewMeasuredHeight);
                            isBottom = true;
                        }
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    public void setAdapter(BaseAdapter adapter) {
        mListNlv.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListNlv.setOnItemClickListener(listener);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mRefreshTop.setOnRefreshListener(listener);
    }

    public void setRefreshing(boolean isOpen) {
        mRefreshTop.setRefreshing(isOpen);
    }

    public void setColorSchemeResources(int... colors) {
        mRefreshTop.setColorSchemeResources(colors);
    }

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    public void setLoadData(OnLoadData loadData) {
        this.loadData = loadData;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        mLoadRl.setVisibility(VISIBLE);
        if (loadData != null) {
            isLoading = true;
            loadData.onLoad();
        }
    }

    public void onLoadComplete() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoading = false;
                mLoadRl.setVisibility(GONE);
            }
        }, 1000);
    }

    public interface OnLoadData {
        void onLoad();
    }

    private boolean isBottom = false;

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    private boolean canLoad() {
        return isBottom && !isLoading && isPullUp();
    }
}
