package com.shop.Android.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.king.Base.KingAdapter;
import com.king.Base.KingFragment;
import com.king.Internet.user_method.CallServer;
import com.king.Utils.ToastUtil;
import com.shop.Android.activity.GoodsDetailActivity;
import com.shop.Android.activity.SpecialSellActivity;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.TimeBean;
import com.shop.Net.Param.GoodsDetail;
import com.shop.Net.Param.SpecialSell;
import com.shop.R;
import com.shop.ShopCar.Goods;
import com.shop.ShopCar.TMShopCar;

/**
 * Created by admin on 2016/9/26.
 */
public class StorageFragment extends KingFragment {

    private String TAG = "storage";
    private AnimNoLineRefreshListView mListLv;
    private TimeBean timeBean;
    private int page = 0;

    @Override
    protected int loadLayout() {
        Post(ActionKey.TIMESELL, new SpecialSell("2", page + ""), TimeBean.class);
        return R.layout.fragment_storage;
    }

    @Override
    public void onStart(String what) {
        switch (what) {
            case ActionKey.TIMESELL:
                break;
            case ActionKey.TIMESELL + "page":
                break;
        }
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.TIMESELL:
                mListLv.onRefreshComplete();
                timeBean = (TimeBean) result;
                if (timeBean.getData().getGoodslist().size() != 0 && timeBean.getData().getGoodslist() != null) {
                    if (adapter == null) {
                        adapter = new TimeAdapter(timeBean.getData().getGoodslist().size(), R.layout.item_storage_list, new TimeViewHolder());
                        mListLv.setAdapter(adapter);
                    } else {
                        adapter.setSize(timeBean.getData().getGoodslist().size());
                        mListLv.setAdapter(adapter);
                    }
                } else {
                    //// TODO: 2016/10/12 灭有数据
                }
                break;
            case ActionKey.TIMESELL + "page":
                mListLv.onLoadComplete();
                TimeBean bean = (TimeBean) result;
                if (bean.getData().getGoodslist().size() != 0 && bean.getData().getGoodslist() != null) {
                    timeBean.getData().getGoodslist().addAll(bean.getData().getGoodslist());
                    if (adapter == null) {
                        adapter = new TimeAdapter(timeBean.getData().getGoodslist().size(), R.layout.item_storage_list, new TimeViewHolder());
                        mListLv.setAdapter(adapter);
                    } else {
                        adapter.setSize(timeBean.getData().getGoodslist().size());
                        mListLv.setAdapter(adapter);
                    }
                } else {
                    SystemToastInfo("没有更多数据了");
                }
                break;
        }
    }

    @Override
    protected void init() {
        F();
        mListLv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                page = 0;
                Post(ActionKey.TIMESELL, new SpecialSell("2", page + ""), TimeBean.class);
            }

            @Override
            public void onLoadMore() {
                page++;
                CallServer.Post(ActionKey.TIMESELL + "page", ActionKey.TIMESELL, new SpecialSell("2", page + ""), TimeBean.class, StorageFragment.this);
            }
        });


    }

    @Override
    protected void onClickSet(int i) {

    }


    private TimeAdapter adapter;

    class TimeViewHolder {
        String TAG = "storage";
        ImageView mIconIv;
        TextView mNameTv;
        TextView mPriceTv;
        TextView mWeightTv;
        TextView mPercengeTv;
        ImageView mCarIv;
        ProgressBar mBarPb;
        RelativeLayout mBgRl;

    }

    private Goods thing = new Goods();

    class TimeAdapter extends KingAdapter {

        public TimeAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            TimeViewHolder viewHolder = (TimeViewHolder) o;
            final TimeBean.DataBean.GoodslistBean dataBean = timeBean.getData().getGoodslist().get(i);
            try {
                Glide(dataBean.getImage(), viewHolder.mIconIv);
                viewHolder.mNameTv.setText(dataBean.getTitle());
                viewHolder.mPriceTv.setText("￥" + dataBean.getActivity_price());
                viewHolder.mWeightTv.setText("参考价￥" + dataBean.getPrice());
                viewHolder.mBarPb.setProgress((Integer.valueOf(dataBean.getSale()) * 100 / Integer.valueOf(dataBean.getStock())));
                viewHolder.mPercengeTv.setText("已售" + viewHolder.mBarPb.getProgress() + "%");
                viewHolder.mBgRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GoodsDetail.goods_id = dataBean.getId();
                        GoodsDetail.type = 2 + "";
                        openActivity(GoodsDetailActivity.class);
                    }
                });
                viewHolder.mCarIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        thing.setId(dataBean.getId());
                        thing.setCount("1");
                        thing.setImage(dataBean.getImage());
                        thing.setTitle(dataBean.getTitle());
                        thing.setPrice(dataBean.getActivity_price());
                        addCart((ImageView) ((RelativeLayout) view.getParent()).getChildAt(0));
                    }
                });
            } catch (Exception e) {

            }

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

    private void addCart(ImageView iv) {
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        if (count < 3) {
            TMShopCar.add(thing);
            TMShopCar.print();
            final ImageView goods = new ImageView(mContext);
            count++;
            goods.setImageDrawable(iv.getDrawable());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
            SpecialSellActivity.mBgRl.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
            //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
            int[] parentLocation = new int[2];
            SpecialSellActivity.mBgRl.getLocationInWindow(parentLocation);

            //得到商品图片的坐标（用于计算动画开始的坐标）
            int startLoc[] = new int[2];
            iv.getLocationInWindow(startLoc);

            //得到购物车图片的坐标(用于计算动画结束后的坐标)
            int endLoc[] = new int[2];
            SpecialSellActivity.mCarLl.getLocationInWindow(endLoc);


//        三、正式开始计算动画开始/结束的坐标
            //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
            final float startX = startLoc[0] - parentLocation[0];
            float startY = startLoc[1] - parentLocation[1];


            //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
            float toX = endLoc[0] - parentLocation[0] + SpecialSellActivity.mCarLl.getWidth() / 5;
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
                    SpecialSellActivity.mRedTv.setText(TMShopCar.getNum() + "");
                    if (Integer.valueOf(SpecialSellActivity.mRedTv.getText().toString().trim()) + 1 > 0) {
                        SpecialSellActivity.mRedTv.setVisibility(View.VISIBLE);
                    } else {
                        SpecialSellActivity.mRedTv.setVisibility(View.GONE);
                    }
                    // 把移动的图片imageview从父布局里移除
                    count--;
                    SpecialSellActivity.mBgRl.removeView(goods);
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
