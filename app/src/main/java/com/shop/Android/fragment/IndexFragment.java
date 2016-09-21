package com.shop.Android.fragment;

import android.graphics.Color;
import android.os.CountDownTimer;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.king.KingApplication;
import com.king.Utils.LogCat;
import com.king.View.gradationscroll.GradationScrollView;
import com.shop.Android.activity.MainActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.PlayViewPager;
import com.shop.R;

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

    private RelativeLayout mRefreshRl;
    private ImageView mRefreshIv;

    private String[] urls = new String[]{
            "http://img12.360buyimg.com/da/jfs/t2695/3/4114524600/99151/7fca55b2/57ad8e86N314092f5.webp",
            "http://img12.360buyimg.com/da/jfs/t2695/3/4114524600/99151/7fca55b2/57ad8e86N314092f5.webp",
            "http://img30.360buyimg.com/da/jfs/t3199/95/2309082525/55586/9688d644/57dfb2feNcb45c27e.jpg"
    };


    @Override
    protected int loadLayout() {
        return R.layout.fragment_index;
    }

    private int height;
    private GestureDetector gesture; //手势识别
    private TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
    private TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f);

    @Override
    protected void init() {

        F();
        mPlayviewPvp.setUrls(urls);
        addTitleSlideChange();
        mListNlv.setAdapter(new TestAdapter(5, R.layout.item_index_list));

        LinearLayoutManager layoutManager = new LinearLayoutManager(KingApplication.getAppContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleRv.setLayoutManager(layoutManager);
        mRecycleRv.setAdapter(new MyAdapter());
        mRecycleRv.setNestedScrollingEnabled(true);
        mRecycleRv.setVisibility(View.VISIBLE);


        setOnClicks(mUpIv);


        //根据父窗体getActivity()为fragment设置手势识别
        gesture = new GestureDetector(this.getActivity(), new MyOnGestureListener());
        //为fragment添加OnTouchListener监听器
        mGradutionGsv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);//返回手势识别触发的事件
            }
        });
        mShowAction.setDuration(500);
        mHiddenAction.setDuration(500);


    }



    //设置手势识别监听器
    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override//此方法必须重写且返回真，否则onFling不起效
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try{
                if ((e1.getY() - e2.getY() > 120) && Math.abs(velocityY) > 200) {
                    return false;
                } else if ((e2.getY() - e1.getY() > 120) && Math.abs(velocityY) > 200) {
                    if(isUp){
                        mRefreshRl.startAnimation(mShowAction);
                        mRefreshRl.setVisibility(View.VISIBLE);
                        new CountDownTimer(3000,1000){

                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                mRefreshRl.startAnimation(mHiddenAction);
                                mRefreshRl.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return false;
        }
    }


    private boolean isUp = true;
    private void addTitleSlideChange() {
        Glide(R.drawable.loading_data, mRefreshIv);
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
                            isUp = true;
                            mUpIv.setVisibility(View.GONE);
                            mTitleLl.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
                        } else if (i1 > 0 && i1 <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                           isUp = false;
                            mUpIv.setVisibility(View.VISIBLE);
                            float scale = (float) i1 / height;
                            float alpha = (255 * scale);
                            mTitleLl.setBackgroundColor(Color.argb((int) alpha, 0xEA, 0x59, 0x3A));
                        } else {    //滑动到banner下面设置普通颜色
                            isUp = false;
                            mUpIv.setVisibility(View.VISIBLE);
                            mTitleLl.setBackgroundColor(Color.argb((int) 255, 0xEA, 0x59, 0x3A));
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ft_index_up_iv:
                mGradutionGsv.smoothScrollTo(0, 0);
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
            return 5;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mContentTv;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
