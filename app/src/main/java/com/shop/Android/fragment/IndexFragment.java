package com.shop.Android.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.king.KingApplication;
import com.king.Utils.LogCat;
import com.king.View.gradationscroll.GradationScrollView;
import com.king.View.refreshview.XRefreshView;
import com.king.View.refreshview.listener.OnBottomLoadMoreTime;
import com.king.View.refreshview.listener.OnTopRefreshTime;
import com.king.View.refreshview.ui.smileyloadingview.SmileyHeaderView;
import com.shop.Android.activity.ClassActivity;
import com.shop.Android.activity.EachOtherActivity;
import com.shop.Android.activity.GoodsClassActivity;
import com.shop.Android.activity.GoodsDetailActivity;
import com.shop.Android.activity.IntegralActivity;
import com.shop.Android.activity.MainActivity;
import com.shop.Android.activity.MsgActivity;
import com.shop.Android.activity.SearchActivity;
import com.shop.Android.activity.SpecialSellActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.widget.MineView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.PlayViewPager;
import com.shop.Android.widget.RefreshView;
import com.shop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by admin on 2016/9/9.
 */
public class IndexFragment extends BaseFragment {

    private String TAG = "index";
    private GradationScrollView mGradutionGsv;
    private PlayViewPager mPlayviewPvp;
    private LinearLayout mTitleLl;
    private RecyclerView mRecycleRv;
    private NoScrollListView mListNlv;
    private ImageView mUpIv;
    private ImageView mMsgIv;
    private ImageView mSearchIv;
    private LinearLayout mSpecialLl;

    private XRefreshView mRefreshXrv;


    private String[] urls = new String[]{
            "http://img12.360buyimg.com/cms/jfs/t3094/223/2369067449/150948/d6251ab7/57e091f4N8ebf5a20.jpg",
            "http://img11.360buyimg.com/cms/jfs/t3085/88/2377100707/165269/ad578270/57e09f82Nbc26248a.jpg",
            "http://img14.360buyimg.com/cms/jfs/t3256/282/2324505968/175172/2448654d/57e09e13Nceacbce1.jpg"
    };


    @Override
    protected int loadLayout() {
        return R.layout.fragment_index;
    }

    private int height;
    private final int mPinnedTime = 0;

    @Override
    protected void init() {
        F();
        mPlayviewPvp.setUrls(urls);
        addTitleSlideChange();
        mListNlv.setAdapter(new TestAdapter(10, R.layout.item_index_list));
        mListNlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openActivity(GoodsClassActivity.class);
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(KingApplication.getAppContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleRv.setLayoutManager(layoutManager);
        mRecycleRv.setAdapter(new MyAdapter());
        mRecycleRv.setNestedScrollingEnabled(true);
        mRecycleRv.setVisibility(View.VISIBLE);


        // 设置是否可以下拉刷新
        mRefreshXrv.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        mRefreshXrv.setPullLoadEnable(false);
        mRefreshXrv.setPinnedTime(mPinnedTime);
        mRefreshXrv.setCustomHeaderView(new RefreshView(mContext));

        mRefreshXrv.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                mTitleLl.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //模拟数据加载失败的情况
                        mRefreshXrv.stopRefresh();
                        mTitleLl.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
            }
        });

        setOnClicks(mUpIv, mMsgIv, mSearchIv, mClassMv, mCreditMv, mPiconeIv, mPictwoIv, mSpecialLl,
                mPicthreeIv, mPicfourIv, mEachMv);
    }

    private void addTitleSlideChange() {
        ViewTreeObserver vto = mPlayviewPvp.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTitleLl.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = mPlayviewPvp.getHeight();

                mGradutionGsv.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(GradationScrollView gradationScrollView, int i, int i1, int i2, int i3) {
                        if (i1 <= 0) {   //设置标题的背景颜色
                            mUpIv.setVisibility(View.GONE);
                            mTitleLl.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
                        } else if (i1 > 0 && i1 <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            mUpIv.setVisibility(View.VISIBLE);
                            float scale = (float) i1 / height;
                            float alpha = (255 * scale);
                            if (alpha > 0xE5) {
                                mTitleLl.setBackgroundColor(Color.argb(0xE5, 0xEA, 0x59, 0x3A));
                            } else {
                                mTitleLl.setBackgroundColor(Color.argb((int) alpha, 0xEA, 0x59, 0x3A));
                            }
                        } else {    //滑动到banner下面设置普通颜色
                            mUpIv.setVisibility(View.VISIBLE);
                            mTitleLl.setBackgroundColor(Color.argb(0xE5, 0xEA, 0x59, 0x3A));
                        }
                    }
                });
            }
        });
    }

    private MineView mClassMv;
    private MineView mCreditMv;
    private ImageView mPiconeIv;
    private ImageView mPictwoIv;
    private ImageView mPicthreeIv;
    private ImageView mPicfourIv;
    private MineView mEachMv;

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ft_index_up_iv:
                mGradutionGsv.smoothScrollTo(0, 0);
                break;
            case R.id.ft_index_msg_iv:
                openActivity(MsgActivity.class);
                break;
            case R.id.ft_index_search_iv:
                openActivity(SearchActivity.class);
                break;
            case R.id.ft_index_class_mv:
                openActivity(ClassActivity.class);
                break;
            case R.id.ft_index_credit_mv:
                openActivity(IntegralActivity.class);
                break;
            case R.id.ft_index_picone_iv:
            case R.id.ft_index_pictwo_iv:
            case R.id.ft_index_picthree_iv:
            case R.id.ft_index_picfour_iv:
                openActivity(GoodsDetailActivity.class);
                break;
            case R.id.ft_index_special_ll:
                openActivity(SpecialSellActivity.class);
                break;
            case R.id.ft_index_each_mv:
                openActivity(EachOtherActivity.class);
                break;
        }

    }


    class MyAdapter extends RecyclerView.Adapter<ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_recycle, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mContentTv;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
