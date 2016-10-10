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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
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
import com.king.Utils.LogCat;
import com.king.Utils.PixelUtil;
import com.king.Utils.UIUtil;
import com.king.View.gradationscroll.GradationScrollView;
import com.king.View.refreshview.XRefreshView;
import com.king.View.refreshview.XScrollView;
import com.shop.Android.adapter.ImagePaperAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.RefreshView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.GoodsDetailBean;
import com.shop.Net.Param.GoodsDetail;
import com.shop.R;
import com.shop.ShopCar.Goods;
import com.shop.ShopCar.ShopCar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntBiFunction;

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

    private TextView mNameTv;
    private TextView mSubtitleTv;
    private TextView mPriceTv;
    private TextView mCommentTv;
    private TextView mIntroTv;


    @Override
    protected int loadLayout() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void initTitleBar() {
        Post(ActionKey.GOODSDETAIL, new GoodsDetail(), GoodsDetailBean.class);
        initTitle("商品详情");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    private boolean isFirst = true;
    private WebView mWebWv;


    private GoodsDetailBean goodsDetailBean;

    private Goods thing = new Goods();

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.GOODSDETAIL:
                goodsDetailBean = (GoodsDetailBean) result;
                if (goodsDetailBean.getCode() == 200) {
                    mNameTv.setText(goodsDetailBean.getData().getTitle());
                    mSubtitleTv.setText(goodsDetailBean.getData().getSubtitled());


                    if(goodsDetailBean.getData().getActivity_price() != null){
                        SpannableString msp = new SpannableString(goodsDetailBean.getData().getActivity_price());
                        msp.setSpan(new RelativeSizeSpan(0.8f), goodsDetailBean.getData().getActivity_price().indexOf(".") + 1, goodsDetailBean.getData().getActivity_price().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                        SpannableString msp1 = new SpannableString("￥" + goodsDetailBean.getData().getPrice() + " ");
                        msp1.setSpan(new RelativeSizeSpan(0.8f), 0, goodsDetailBean.getData().getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                        msp1.setSpan(new StrikethroughSpan(), 0, goodsDetailBean.getData().getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        msp1.setSpan(new ForegroundColorSpan(Color.rgb(0x98, 0x98, 0x98)), 0, goodsDetailBean.getData().getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mPriceTv.setText("");
                        mPriceTv.append("￥");
                        mPriceTv.append(msp);
                        mPriceTv.append("  ");
                        mPriceTv.append(msp1);
                        mPriceTv.append(" ");
                    }else {
                        mPriceTv.setText("￥" + goodsDetailBean.getData().getPrice());
                        SpannableString msp = new SpannableString(mPriceTv.getText().toString());
                        msp.setSpan(new RelativeSizeSpan(0.7f), mPriceTv.getText().toString().indexOf(".") + 1, mPriceTv.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                        mPriceTv.setText(msp);
                    }

                    mIntroTv.setText(goodsDetailBean.getData().getIntro());
                    mCommentTv.setText("" + goodsDetailBean.getData().getComment_num() + "人评价");
                    addViewPager();
                    addRecycleView();
                } else {
                    ToastInfo("网络未知错误");
                }
                break;
        }
    }

    @Override
    protected void init() {
        F();

        if(ShopCar.getNum() > 0){
            mRedTv.setVisibility(View.VISIBLE);
        }
        mRedTv.setText(ShopCar.getNum() + "");

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

    private Goods thing1 = new Goods();

    class HotAdapter extends RecyclerView.Adapter<ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_igoodsdetail_recycle, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final GoodsDetailBean.DataBean.RecommendBean recommendBean = goodsDetailBean.getData().getRecommend().get(position);
            Glide(recommendBean.getImage(), holder.mIconIv);
            holder.mNameTv.setText(recommendBean.getTitle());

            holder.mPriceTv.setText("￥" + recommendBean.getPrice());
            SpannableString msp = new SpannableString(holder.mPriceTv.getText().toString());
            msp.setSpan(new RelativeSizeSpan(0.7f), holder.mPriceTv.getText().toString().indexOf(".") + 1, holder.mPriceTv.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
            holder.mPriceTv.setText(msp);

            holder.mBgLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoodsDetail.type = "1";
                    GoodsDetail.goods_id = recommendBean.getId() + "";
                    openActivity(GoodsDetailActivity.class);
                }
            });

            holder.mCarIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    thing1.setId(recommendBean.getId() + "");
                    thing1.setCount("1");
                    thing1.setImage(recommendBean.getImage());
                    thing1.setTitle(recommendBean.getTitle());
                    thing1.setSubTitle(recommendBean.getSubtitled());
                    thing1.setPrice(recommendBean.getPrice());
                    addCart1((ImageView) ((LinearLayout)(view.getParent().getParent())).getChildAt(0));
                }
            });

        }

        @Override
        public int getItemCount() {
            return goodsDetailBean.getData().getRecommend().size();
        }
    }


    private void addCart1(ImageView iv) {
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        if (count < 3) {
            ShopCar.add(thing1);
            ShopCar.print();
            final ImageView goods = new ImageView(mContext);
            count++;
            goods.setImageDrawable(iv.getDrawable());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
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
            float startX = startLoc[0] - parentLocation[0];
            float startY = startLoc[1] - parentLocation[1];

            //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
            float toX = endLoc[0] - parentLocation[0] + mCarLl.getWidth() / 5;
            float toY = endLoc[1] - parentLocation[1];

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
            //开始绘制贝塞尔曲线
            Path path = new Path();
            //移动到起始点（贝塞尔曲线的起点）
            path.moveTo(startX, startY);
            //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
            path.quadTo((startX + toX) / 2, startY, toX, toY);
            //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
            // 如果是true，path会形成一个闭环
            mPathMeasure = new PathMeasure(path, false);

            //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
            valueAnimator.setDuration(1000);
            // 匀速线性插值器
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // 当插值计算进行时，获取中间的每个值，
                    // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                    float value = (Float) animation.getAnimatedValue();
                    // ★★★★★获取当前点坐标封装到mCurrentPosition
                    // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                    // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                    // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                    mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                    // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                    goods.setTranslationX(mCurrentPosition[0]);
                    goods.setTranslationY(mCurrentPosition[1]);
                }
            });
            ObjectAnimator animtion3 = ObjectAnimator.ofFloat(goods, "rotation", 0, 360);
            animtion3.setDuration(200);
            animtion3.setRepeatCount(5);
            //      五、 开始执行动画
            AnimatorSet set = new AnimatorSet();    //创建动画集对象
            set.playTogether(valueAnimator, animtion3);       //添加位置变化动画
            set.setDuration(1300).start();


            //      六、动画结束后的处理
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                //当动画结束后：
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRedTv.setText(Integer.valueOf(mRedTv.getText().toString().trim()) + 1 + "");
                    if(Integer.valueOf(mRedTv.getText().toString().trim()) + 1  > 0){
                        mRedTv.setVisibility(View.VISIBLE);
                    }else {
                        mRedTv.setVisibility(View.GONE);
                    }
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


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mIconIv;
        TextView mNameTv;
        TextView mPriceTv;
        ImageView mCarIv;
        LinearLayout mBgLl;

        public ViewHolder(View itemView) {
            super(itemView);
            mIconIv = UIUtil.findViewById(itemView, R.id.item_hist_icon_iv);
            mNameTv = UIUtil.findViewById(itemView, R.id.item_hist_name_tv);
            mPriceTv = UIUtil.findViewById(itemView, R.id.item_hist_price_tv);
            mCarIv = UIUtil.findViewById(itemView, R.id.item_hist_car_iv);
            mBgLl = UIUtil.findViewById(itemView,R.id.item_hist_bg_ll);
        }
    }


    private List<ImageView> list;
    private LinearLayout mPointLl;

    private void addViewPager() {
        list = new ArrayList<>();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mScreenWidth, mScreenWidth);
        mPicVp.setLayoutParams(layoutParams);

        //填充点下的点，固定代码
        if (goodsDetailBean.getData().getDetail_image().size() > 1) {
            for (int i = 0; i < goodsDetailBean.getData().getDetail_image().size(); i++) {
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
        }
        for (int i = 0; i < goodsDetailBean.getData().getDetail_image().size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_image_vp, null);
            ImageView image = (ImageView) view.findViewById(R.id.img);
            Glide(goodsDetailBean.getData().getDetail_image().get(i), image);
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
            int index = position % goodsDetailBean.getData().getDetail_image().size();
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
                MainActivity.index = 1;
                openActivity(MainActivity.class);
                break;
            case R.id.ay_detail_add_tv:
                try{
                    thing.setId(goodsDetailBean.getData().getId());
                    thing.setCount("1");
                    thing.setImage(goodsDetailBean.getData().getImage());
                    thing.setTitle(goodsDetailBean.getData().getTitle());
                    thing.setSubTitle(goodsDetailBean.getData().getSubtitled());
                    thing.setPrice(goodsDetailBean.getData().getPrice());
                }catch (Exception e){
                    e.printStackTrace();
                }

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
            ShopCar.add(thing);
            ShopCar.print();
            final ImageView goods = new ImageView(mContext);
            count++;
            GlideCircle(goodsDetailBean.getData().getDetail_image().get(mPicVp.getCurrentItem()), goods);
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
                    if(Integer.valueOf(mRedTv.getText().toString().trim()) + 1  > 0){
                        mRedTv.setVisibility(View.VISIBLE);
                    }else {
                        mRedTv.setVisibility(View.GONE);
                    }
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
