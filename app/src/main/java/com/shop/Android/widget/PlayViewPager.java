package com.shop.Android.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.king.Base.KingActivity;
import com.king.Base.KingFragment;
import com.king.Utils.PictureUtil;
import com.king.Utils.PixelUtil;
import com.shop.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rwq on 2016/6/12.
 */
public class PlayViewPager extends RelativeLayout {

    //填充的数据
    private int[] picture_vp = new int[]{R.drawable.default_image, R.drawable.default_image, R.drawable.default_image};

    private String[] urls;
    private String[] text_vp = new String[]{
            "我的界面我做主", "傻逼的生活让人难以理解", "愿得一人心，白首不相离", "看什么看sb"};

    private ViewPager mVp;
    private MyAdapter mAdapter;
    //    private TextView mText;
    private LinearLayout mPoint;
    private List<View> mListViews;
    private int lastPosition;
    private boolean isRunning = false;
    private boolean isChange = false;
    private Context context;
    private int index;
    private Listener listener;

    private long delayTime = 2000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mVp.setCurrentItem(mVp.getCurrentItem() + 1);
            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, delayTime);
            }
        }
    };


    private void initView(final Context context) {
        this.context = context;
        View.inflate(context, R.layout.play_viewpager, this);
        setupView(context);
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
        mListViews.clear();
        mPoint.removeAllViews();
        //填充图片，固定代码
        for (int i = 0; i < urls.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            PictureUtil.Glide(urls[i], imageView);
            mListViews.add(imageView);
        }
        //填充点下的点，固定代码
        for (int i = 0; i < urls.length; i++) {
            ImageView point = new ImageView(context);
            point.setBackgroundResource(R.drawable.point_new_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            params.leftMargin = 20;
            point.setLayoutParams(params);
            mPoint.addView(point);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    private void setupView(Context context) {
        mVp = (ViewPager) findViewById(R.id.vp);
//        mText = (TextView) findViewById(R.id.vp_tv);
        mPoint = (LinearLayout) findViewById(R.id.vp_point);
        mListViews = new ArrayList<>();
        //设置第一张图片下的文字
//        mText.setText(text_vp[0]);

        //填充图片，固定代码
        for (int i = 0; i < picture_vp.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(picture_vp[i]);
            mListViews.add(imageView);
        }
        //填充点下的点，固定代码
        for (int i = 0; i < picture_vp.length; i++) {
            ImageView point = new ImageView(context);
            point.setBackgroundResource(R.drawable.point_new_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            params.leftMargin = 15;
            point.setLayoutParams(params);
            mPoint.addView(point);
            if (i == 0) {
                lastPosition = i;
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
        }
        mAdapter = new MyAdapter();
        mVp.setAdapter(mAdapter);
        mVp.setCurrentItem(0);
        //设置中间值
        index = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mListViews.size();
        mVp.setCurrentItem(index);
        listener = new Listener();
        mVp.addOnPageChangeListener(listener);
        isRunning = true;
        handler.sendEmptyMessageDelayed(0, delayTime);
    }


    public PlayViewPager(Context context) {
        super(context);
        initView(context);
    }

    public PlayViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    class MyAdapter extends PagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (container != null) {
                container.removeView(mListViews.get(position % mListViews.size()));
            }
            container.addView(mListViews.get(position % mListViews.size()));
            return mListViews.get(position % mListViews.size());
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//官方提示这样写
        }

    }

    class Listener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int index = position % mListViews.size();
//                String msg = text_vp[IndexBean];
//                mText.setText(msg);
            for(int i = 0;i < mPoint.getChildCount();i ++){
                mPoint.getChildAt(i).setEnabled(false);
            }
            try{
                if (index == lastPosition) {
                    mPoint.getChildAt(index + 1).setEnabled(true);
                    mPoint.getChildAt(lastPosition).setEnabled(false);
                    lastPosition = index + 1;
                } else {
                    mPoint.getChildAt(index).setEnabled(true);
                    mPoint.getChildAt(lastPosition).setEnabled(false);
                    lastPosition = index;
                }

            }catch (Exception e){
                mPoint.getChildAt(0).setEnabled(true);
                mPoint.getChildAt(lastPosition).setEnabled(false);
                lastPosition = 0;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 1) {
                isChange = true;
                handler.removeMessages(0);
            } else if (isChange) {
                handler.sendEmptyMessageDelayed(0, delayTime);
                isChange = false;
            }

        }
    }
}
