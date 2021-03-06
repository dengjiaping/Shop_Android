package com.xiaojishop.Android.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.king.Base.KingData;
import com.king.KingApplication;
import com.king.Utils.GsonUtil;
import com.king.Utils.PixelUtil;
import com.king.Utils.SPrefUtil;
import com.king.Utils.UIUtil;
import com.king.View.gradationscroll.GradationScrollView;
import com.king.View.refreshview.XRefreshView;
import com.king.View.refreshview.XScrollView;
import com.xiaojishop.Android.Config;
import com.xiaojishop.Android.SPKey;
import com.xiaojishop.Android.adapter.ImagePaperAdapter;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Android.widget.ClassView.adapter.ShopAdapter;
import com.xiaojishop.Android.widget.ClassView.mode.ShopProduct;
import com.xiaojishop.Net.ActionKey;
import com.xiaojishop.Net.Bean.BaseBean;
import com.xiaojishop.Net.Bean.GoodsDetailBean;
import com.xiaojishop.Net.Bean.IndexBean;
import com.xiaojishop.Net.Param.Collect;
import com.xiaojishop.Net.Param.GoodsDetail;
import com.xiaojishop.R;
import com.xiaojishop.ShopCar.Goods;
import com.xiaojishop.ShopCar.ShopCar;
import com.xiaojishop.ShopCar.TMShopCar;

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

    private TextView mNameTv;
    private TextView mSubtitleTv;
    private TextView mPriceTv;
    private TextView mCommentTv;
    private TextView mIntroTv;
    private TextView mOrderTv;


    private TextView mDefaultTv;
    private FrameLayout mCardFl;
    private LinearLayout mCardshopLl;
    /**
     * 背景View
     */
    private View mBgV;
    private ListView mShoplistLv;

    private TextView mStatusTv;
    private TextView mTimeTv;
    private TextView mShopTv;
    private TextView mHotTv;
    private RelativeLayout mZgRl;


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

    private String TYPE = "1";

    private GoodsDetailBean goodsDetailBean;

    private Goods thing = new Goods();

    @Override
    protected void onResume() {
        super.onResume();
        if (TYPE.equals("1")) {
            if (ShopCar.getNum() > 0) {
                mRedTv.setVisibility(View.VISIBLE);
            }
            mRedTv.setText(ShopCar.getNum() + "");
        } else {
            if (TMShopCar.getNum() > 0) {
                mRedTv.setVisibility(View.VISIBLE);
            } else {
                mRedTv.setVisibility(View.GONE);
            }
            mRedTv.setText(TMShopCar.getNum() + "");
        }


        kingData.registerWatcher("NUM", new KingData.KingCallBack() {
            @Override
            public void onChange() {
                if (TMShopCar.getNum() != 0) {
                    mRedTv.setText(TMShopCar.getNum() + "");
                } else {
                    mRedTv.setVisibility(View.GONE);
                    mDefaultTv.setVisibility(View.VISIBLE);
                }
            }
        });

        kingData.registerWatcher("REDUCE", new KingData.KingCallBack() {
            @Override
            public void onChange() {
                productList = new ArrayList<>();
                for (String key : TMShopCar.getKeys()) {
                    ShopProduct shopProduct = new ShopProduct();
                    shopProduct.setId(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getId());
                    shopProduct.setGoods(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getTitle());
                    shopProduct.setPrice(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getPrice());
                    shopProduct.setPicture(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getImage());
                    shopProduct.setType(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getSubTitle());
                    shopProduct.setNumber(Integer.parseInt(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getCount()));
                    productList.add(shopProduct);
                }
                shopAdapter = new ShopAdapter(mContext, productList);
                mShoplistLv.setAdapter(shopAdapter);

                if (TMShopCar.getMap().size() == 0) {
                    mRedTv.setVisibility(View.GONE);
                    mDefaultTv.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private ImageView mStarIv;
    private TextView mCollectTv;
    private LinearLayout mCollectLl;

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.ADDCOLLECT:
                BaseBean baseBean = (BaseBean) result;
                if (baseBean.getCode() == 200) {
                    //已收藏
                    isCollect = true;
                    mStarIv.setImageResource(R.drawable.yellow_star);
                    mCollectTv.setTextColor(Color.rgb(0xFF, 0x9C, 0x47));
                    ToastInfo("收藏成功");
                } else if (baseBean.getCode() == 2001) {
                    ToastInfo(baseBean.getMsg());
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(baseBean.getMsg());
                }
                break;
            case ActionKey.CANCELCOLLECT:
                baseBean = (BaseBean) result;
                if (baseBean.getCode() == 200) {
                    //已收藏
                    isCollect = false;
                    mStarIv.setImageResource(R.drawable.gray_star);
                    mCollectTv.setTextColor(Color.rgb(0x88, 0x88, 0x88));
                    ToastInfo("取消收藏成功");
                    kingData.sendBroadCast(Config.COLLECT);
                } else if (baseBean.getCode() == 2001) {
                    ToastInfo(baseBean.getMsg());
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(baseBean.getMsg());
                }
                break;
            case ActionKey.GOODSDETAIL:
                goodsDetailBean = (GoodsDetailBean) result;
                if (goodsDetailBean.getCode() == 200) {
                    mNameTv.setText(goodsDetailBean.getData().getTitle() + " " + goodsDetailBean.getData().getSubtitled());
                    mSubtitleTv.setText(goodsDetailBean.getData().getUnit());
                    if (goodsDetailBean.getData().getActivity_price() != null) {
                        TYPE = "2";
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
                    } else {
                        TYPE = "1";
                        SpannableString msp = new SpannableString(goodsDetailBean.getData().getPre_price());
                        msp.setSpan(new RelativeSizeSpan(0.8f), goodsDetailBean.getData().getPre_price().indexOf(".") + 1, goodsDetailBean.getData().getPre_price().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
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
                    }

                    if (TYPE.equals("1")) {
                        if (ShopCar.getNum() > 0) {
                            mRedTv.setVisibility(View.VISIBLE);
                        }
                        mRedTv.setText(ShopCar.getNum() + "");
                    } else {
                        if (TMShopCar.getNum() > 0) {
                            mRedTv.setVisibility(View.VISIBLE);
                        } else {
                            mRedTv.setVisibility(View.GONE);
                        }
                        mRedTv.setText(TMShopCar.getNum() + "");
                    }
//    #FF9C47 #888

                    if (goodsDetailBean.getData().getIs_collect().equals("0")) {
                        //没有收藏
                        mStarIv.setImageResource(R.drawable.gray_star);
                        mCollectTv.setTextColor(Color.rgb(0x88, 0x88, 0x88));
                        isCollect = false;
                    } else {
                        //已收藏
                        mStarIv.setImageResource(R.drawable.yellow_star);
                        mCollectTv.setTextColor(Color.rgb(0xFF, 0x9C, 0x47));
                        isCollect = true;
                    }
                    if (goodsDetailBean.getData().getRemark().equals("") || goodsDetailBean.getData().getRemark() == null) {
                        mZgRl.setVisibility(View.GONE);
                    } else {
                        mIntroTv.setText(goodsDetailBean.getData().getRemark());
                    }
                    mCommentTv.setText("" + goodsDetailBean.getData().getComment_num() + "人评价");
                    addViewPager();
                    addRecycleView();
                } else {
                    ToastInfo("网络未知错误");
                }
                break;
        }
    }

    private ShopAdapter shopAdapter;
    /**
     * 保存购物车对象到List
     * TODO:考虑保存购物车缓存
     */
    private List<ShopProduct> productList;

    @Override
    protected void init() {
        F();

        mShopTv.setText(((IndexBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX, ""), IndexBean.class)).getData().getShop().getName());
        mTimeTv.setText("营业时间:" + ((IndexBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX, ""), IndexBean.class)).getData().getShop().getBegin_business_time() + "~" + ((IndexBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX, ""), IndexBean.class)).getData().getShop().getEnd_business_time());
        if (((IndexBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX, ""), IndexBean.class)).getData().getShop().getStatus() == 0) {
            mStatusTv.setText("未营业");
        } else {
            mStatusTv.setText("营业中");
        }

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
                mWebWv.loadUrl(goodsDetailBean.getData().getUrl());
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
        setOnClicks(mCarLl, mAddTv, mUpIv, mBgV, mOrderTv, mCollectLl, mCommentTv);


    }

    private void addRecycleView() {
        if (goodsDetailBean.getData().getRecommend().size() == 0 || goodsDetailBean.getData().getRecommend() == null) {
            mHotRv.setVisibility(View.GONE);
            mHotTv.setVisibility(View.GONE);
            return;
        }
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
            holder.mNameTv.setText(recommendBean.getTitle() + " " + recommendBean.getSubtitled());

            if (recommendBean.getActivity_price() != null) {
                SpannableString msp = new SpannableString(recommendBean.getActivity_price());
                msp.setSpan(new RelativeSizeSpan(0.8f), recommendBean.getActivity_price().indexOf(".") + 1, recommendBean.getActivity_price().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                SpannableString msp1 = new SpannableString("￥" + recommendBean.getPrice() + " ");
                msp1.setSpan(new RelativeSizeSpan(0.8f), 0, recommendBean.getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                msp1.setSpan(new StrikethroughSpan(), 0, recommendBean.getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp1.setSpan(new ForegroundColorSpan(Color.rgb(0x98, 0x98, 0x98)), 0, recommendBean.getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mPriceTv.setText("");
                holder.mPriceTv.append("￥");
                holder.mPriceTv.append(msp);
                holder.mPriceTv.append("  ");
                holder.mPriceTv.append(msp1);
                holder.mPriceTv.append(" ");
            } else {
                SpannableString msp = new SpannableString(goodsDetailBean.getData().getPre_price());
                msp.setSpan(new RelativeSizeSpan(0.8f), goodsDetailBean.getData().getPre_price().indexOf(".") + 1, goodsDetailBean.getData().getPre_price().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                SpannableString msp1 = new SpannableString("￥" + goodsDetailBean.getData().getPrice() + " ");
                msp1.setSpan(new RelativeSizeSpan(0.8f), 0, goodsDetailBean.getData().getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                msp1.setSpan(new StrikethroughSpan(), 0, goodsDetailBean.getData().getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp1.setSpan(new ForegroundColorSpan(Color.rgb(0x98, 0x98, 0x98)), 0, goodsDetailBean.getData().getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mPriceTv.setText("");
                holder.mPriceTv.append("￥");
                holder.mPriceTv.append(msp);
                holder.mPriceTv.append("  ");
                holder.mPriceTv.append(msp1);
                holder.mPriceTv.append(" ");
            }


            holder.mBgLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoodsDetail.type = TYPE;
                    GoodsDetail.goods_id = recommendBean.getId() + "";
                    openActivity(GoodsDetailActivity.class);
                }
            });

            holder.mCarIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TYPE.equals("1")) {
                        thing1.setId(recommendBean.getId() + "");
                        thing1.setCount("1");
                        thing1.setImage(recommendBean.getImage());
                        thing1.setTitle(recommendBean.getTitle());
                        thing1.setSubTitle(recommendBean.getSubtitled());
                        thing1.setPrice(recommendBean.getPrice());
                        thing1.setType(recommendBean.getType());
                    } else {
                        thing1.setId(recommendBean.getId() + "");
                        thing1.setCount("1");
                        thing1.setImage(recommendBean.getImage());
                        thing1.setTitle(recommendBean.getTitle());
                        thing1.setSubTitle(recommendBean.getSubtitled());
                        thing1.setPrice(recommendBean.getActivity_price());
                        thing1.setType(recommendBean.getType());
                    }
                    addCart1((ImageView) ((LinearLayout) (view.getParent().getParent())).getChildAt(0));
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
            if (TYPE.equals("1")) {
                ShopCar.add(thing1);
                ShopCar.print();
            } else {
                TMShopCar.add(thing1);
                TMShopCar.print();
            }

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
                    mRedTv.setText(TMShopCar.getNum() + "");
                    if (Integer.valueOf(mRedTv.getText().toString().trim()) + 1 > 0) {
                        mRedTv.setVisibility(View.VISIBLE);
                    } else {
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
            mBgLl = UIUtil.findViewById(itemView, R.id.item_hist_bg_ll);
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

    private boolean isCollect = false;

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_detail_collect_ll:
                if (isCollect) {
                    Post(ActionKey.CANCELCOLLECT, new Collect(goodsDetailBean.getData().getId()), BaseBean.class);
                } else {
                    Post(ActionKey.ADDCOLLECT, new Collect(goodsDetailBean.getData().getId()), BaseBean.class);
                }
                break;
            case R.id.ay_detail_car_ll:
                if (TYPE.equals("1")) {
                    MainActivity.index = 1;
                    openActivity(MainActivity.class);
                } else {
                    if (TMShopCar.getMap().size() == 0) {
                        mDefaultTv.setVisibility(View.VISIBLE);
                    } else {
                        mDefaultTv.setVisibility(View.GONE);
                        productList = new ArrayList<>();
                        for (String key : TMShopCar.getKeys()) {
                            ShopProduct shopProduct = new ShopProduct();
                            shopProduct.setId(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getId());
                            shopProduct.setGoods(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getTitle());
                            shopProduct.setPrice(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getPrice());
                            shopProduct.setPicture(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getImage());
                            shopProduct.setType(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getSubTitle());
                            shopProduct.setNumber(Integer.parseInt(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getCount()));
                            productList.add(shopProduct);
                        }
                        shopAdapter = new ShopAdapter(mContext, productList);
                        mShoplistLv.setAdapter(shopAdapter);
                    }
                    if (mCardFl.getVisibility() == View.GONE) {
                        mCardFl.setVisibility(View.VISIBLE);

                        // 加载动画
                        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.push_bottom_in);
                        // 动画开始
                        mCardshopLl.setVisibility(View.VISIBLE);
                        mCardshopLl.startAnimation(animation);
                        mBgV.setVisibility(View.VISIBLE);
                        mOrderTv.setVisibility(View.VISIBLE);
                    } else {
                        mCardFl.setVisibility(View.GONE);
                        mBgV.setVisibility(View.GONE);
                        mCardshopLl.setVisibility(View.GONE);
                        mOrderTv.setVisibility(View.INVISIBLE);
                    }

                }
                break;
            case R.id.ay_detail_add_tv:
                try {
                    if (TYPE.equals("1")) {
                        thing.setId(goodsDetailBean.getData().getId());
                        thing.setCount("1");
                        thing.setImage(goodsDetailBean.getData().getImage());
                        thing.setTitle(goodsDetailBean.getData().getTitle());
                        thing.setSubTitle(goodsDetailBean.getData().getSubtitled());
                        thing.setPrice(goodsDetailBean.getData().getPrice());
                        thing.setType(goodsDetailBean.getData().getType());
                    } else {
                        thing.setId(goodsDetailBean.getData().getActivity_id());
                        thing.setCount("1");
                        thing.setImage(goodsDetailBean.getData().getImage());
                        thing.setTitle(goodsDetailBean.getData().getTitle());
                        thing.setSubTitle(goodsDetailBean.getData().getSubtitled());
                        thing.setPrice(goodsDetailBean.getData().getActivity_price());
                        thing.setType(goodsDetailBean.getData().getType());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                addCart(mAddTv);

                productList = new ArrayList<>();
                for (String key : TMShopCar.getKeys()) {
                    ShopProduct shopProduct = new ShopProduct();
                    shopProduct.setId(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getId());
                    shopProduct.setGoods(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getTitle());
                    shopProduct.setPrice(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getPrice());
                    shopProduct.setPicture(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getImage());
                    shopProduct.setType(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getSubTitle());
                    shopProduct.setNumber(Integer.parseInt(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getCount()));
                    productList.add(shopProduct);
                }
                shopAdapter = new ShopAdapter(mContext, productList);
                mShoplistLv.setAdapter(shopAdapter);

                if (TMShopCar.getMap().size() != 0) {
                    mDefaultTv.setVisibility(View.GONE);
                }

                break;
            case R.id.ay_detail_up_iv:
                mScrollXsc.smoothScrollTo(0, 0);
                break;

            case R.id.ay_detail_bg_v:
                mCardFl.setVisibility(View.GONE);
                mBgV.setVisibility(View.GONE);
                mCardshopLl.setVisibility(View.GONE);
                mOrderTv.setVisibility(View.INVISIBLE);
                break;
            case R.id.ay_detail_order_tv:
                if (TMShopCar.getNum() == 0) {
                    ToastInfo("您还未选择任何商品");
                    return;
                }
                SubmitOrderActivity.TYPE = 0;
                openActivity(SubmitOrderActivity.class);
                animFinsh();
                break;
            case R.id.ay_detail_comment_tv:
//                Intent intent = new Intent(mContext, MineGoodsEvaluateActivity.class);
//                intent.putExtra("id", goodsDetailBean.getData().getId());
//                startActivity(intent);
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
            if (TYPE.equals("1")) {
                ShopCar.add(thing);
                ShopCar.print();
            } else {
                TMShopCar.add(thing);
                TMShopCar.print();
            }
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
                    if (TYPE.equals("1")) {
                        mRedTv.setText(ShopCar.getNum() + "");
                    } else {
                        mRedTv.setText(TMShopCar.getNum() + "");
                    }
                    if (Integer.valueOf(mRedTv.getText().toString().trim()) + 1 > 0) {
                        mRedTv.setVisibility(View.VISIBLE);
                    } else {
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
