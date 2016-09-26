package com.shop.Android.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.king.KingApplication;
import com.king.Utils.PixelUtil;
import com.king.View.gradationscroll.GradationScrollView;
import com.king.View.refreshview.XRefreshView;
import com.king.View.refreshview.XScrollView;
import com.shop.Android.adapter.ImagePaperAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.RefreshView;
import com.shop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/23.
 */
public class GoodsDetailActivity extends BaseActvity {

    private String TAG = "detail";
    private ViewPager mPicVp;
    private RecyclerView mHotRv;
    private LinearLayout mCarLl;
    private TextView mAddTv;
    private XRefreshView mRefreshXrv;
    private XScrollView mScrollXsc;
    private TextView mMoreTv;
    private ImageView mUpIv;


    @Override
    protected int loadLayout() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void initTitleBar() {
        initTitle("商品详情");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    private boolean isFirst = true;
    private WebView mWebWv;

    @Override
    protected void init() {
        F();

        // 设置是否可以下拉刷新
        mRefreshXrv.setPullRefreshEnable(false);
        // 设置是否可以上拉加载
        mRefreshXrv.setPullLoadEnable(true);
        mRefreshXrv.setPinnedTime(1000);

        mRefreshXrv.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore(boolean isSilence) {
                mWebWv.loadUrl("http://www.baidu.com/");
                mWebWv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // TODO Auto-generated method stub
                        //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                        view.loadUrl(url);
                        return true;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mWebWv.setVisibility(View.VISIBLE);
                                mScrollXsc.smoothScrollTo(0, mScrollXsc.getScrollY() + mScreenHeight);
                                mRefreshXrv.stopLoadMore();
                                // 设置是否可以上拉加载
                                mRefreshXrv.setPullLoadEnable(false);
                                mMoreTv.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                });

            }
        });

        mScrollXsc.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(GradationScrollView gradationScrollView, int i, int i1, int i2, int i3) {
                if (i1 <= 0) {   //设置标题的背景颜色
                    mUpIv.setVisibility(View.GONE);
                } else if (i1 > 0) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    mUpIv.setVisibility(View.VISIBLE);
                }
            }
        });
        addViewPager();
        addRecycleView();
        setOnClicks(mCarLl, mAddTv, mUpIv);

    }

    private void addRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(KingApplication.getAppContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHotRv.setLayoutManager(layoutManager);
        mHotRv.setAdapter(new HotAdapter());
        mHotRv.setNestedScrollingEnabled(true);
        mHotRv.setVisibility(View.VISIBLE);
        mHotRv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        NoSlide();
                        break;
                    case MotionEvent.ACTION_UP:
                        CanSlide();
                        break;
                }
                return false;
            }
        });
    }


    class HotAdapter extends RecyclerView.Adapter<ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_igoodsdetail_recycle, parent, false);
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


    private List<ImageView> list;
    private String[] urls = new String[]{
            "http://img12.360buyimg.com/cms/jfs/t3094/223/2369067449/150948/d6251ab7/57e091f4N8ebf5a20.jpg",
            "http://img11.360buyimg.com/cms/jfs/t3085/88/2377100707/165269/ad578270/57e09f82Nbc26248a.jpg",
            "http://img14.360buyimg.com/cms/jfs/t3256/282/2324505968/175172/2448654d/57e09e13Nceacbce1.jpg"
    };
    private LinearLayout mPointLl;

    private void addViewPager() {
        list = new ArrayList<>();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mScreenWidth, mScreenWidth);
        mPicVp.setLayoutParams(layoutParams);

        //填充点下的点，固定代码
        for (int i = 0; i < urls.length; i++) {
            ImageView point = new ImageView(mContext);
            point.setBackgroundResource(R.drawable.point_new_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            params.leftMargin = 15;
            point.setLayoutParams(params);
            mPointLl.addView(point);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
        }
        for (int i = 0; i < urls.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_image_vp, null);
            ImageView image = (ImageView) view.findViewById(R.id.img);
            Glide(urls[i], image);
            list.add(image);
        }
        ImagePaperAdapter adapter = new ImagePaperAdapter((ArrayList) list);
        mPicVp.setAdapter(adapter);
        listener = new Listener();
        mPicVp.addOnPageChangeListener(listener);
        mPicVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        NoSlide();
                        break;
                    case MotionEvent.ACTION_UP:
                        CanSlide();
                        break;
                }
                return false;
            }
        });
    }

    private Listener listener;

    class Listener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int index = position % urls.length;
//                String msg = text_vp[IndexBean];
//                mText.setText(msg);
            for (int i = 0; i < mPointLl.getChildCount(); i++) {
                mPointLl.getChildAt(i).setEnabled(false);
            }
            mPointLl.getChildAt(index).setEnabled(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_detail_car_ll:
                openDataAct(MainActivity.class, "1");
                break;
            case R.id.ay_detail_add_tv:
                addCart(mAddTv);
                break;
            case R.id.ay_detail_up_iv:
                mScrollXsc.smoothScrollTo(0, 0);
                break;
        }

    }


    /**
     * ★★★★★把商品添加到购物车的动画效果★★★★★
     *
     * @param iv
     */
    private PathMeasure mPathMeasure;
    /**
     * 贝塞尔曲线中间过程的点的坐标
     */
    private float[] mCurrentPosition = new float[2];
    private int count = 0;
    private RelativeLayout mBgRl;
    private TextView mRedTv;


    private void addCart(View iv) {
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        if (count < 3) {
            final ImageView goods = new ImageView(mContext);
            count++;
            GlideCircle(urls[mPicVp.getCurrentItem()], goods);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
            mBgRl.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
            //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
            int[] parentLocation = new int[2];
            mBgRl.getLocationInWindow(parentLocation);

            //得到商品图片的坐标（用于计算动画开始的坐标）
            int startLoc[] = new int[2];
            iv.getLocationInWindow(startLoc);

            //得到购物车图片的坐标(用于计算动画结束后的坐标)
            int endLoc[] = new int[2];
            mCarLl.getLocationInWindow(endLoc);


//        三、正式开始计算动画开始/结束的坐标
            //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
            final float startX = startLoc[0] - parentLocation[0];
            float startY = startLoc[1] - parentLocation[1];


            //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
            float toX = endLoc[0] - parentLocation[0] + mCarLl.getWidth() / 5;
            float toY = endLoc[1] - parentLocation[1];
            ObjectAnimator translateAnimationX = ObjectAnimator.ofFloat(goods, "translationX", startX, toX);
            translateAnimationX.setDuration(1000);
            translateAnimationX.setInterpolator(new LinearInterpolator());
            ObjectAnimator translateAnimationY = ObjectAnimator.ofFloat(goods, "translationY", startY, mScreenHeight - PixelUtil.dp2px(230), startY);
            translateAnimationY.setDuration(500);
            translateAnimationY.setInterpolator(new LinearInterpolator());
            ObjectAnimator animtion3 = ObjectAnimator.ofFloat(goods, "rotation", 0, 360);
            animtion3.setDuration(1000);
            //      五、 开始执行动画
            AnimatorSet set = new AnimatorSet();
            set.play(translateAnimationX).with(translateAnimationY).with(animtion3);
            set.setDuration(1000).start();


            //      六、动画结束后的处理
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                //当动画结束后：
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRedTv.setText(Integer.valueOf(mRedTv.getText().toString().trim()) + 1 + "");
                    // 把移动的图片imageview从父布局里移除
                    count--;
                    mBgRl.removeView(goods);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            Toast.makeText(mContext, "亲,您添加购物车好辛苦,休息一下", Toast.LENGTH_SHORT).show();
        }

    }

}
