package com.shop.Android.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.media.Image;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingActivity;
import com.king.Base.KingAdapter;
import com.king.Config;
import com.king.Dialog.CustomDialog;
import com.king.Utils.DialogUtil;
import com.king.Utils.PixelUtil;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.widget.BgTextView;
import com.shop.Android.widget.NoScrollGridView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.R;

import org.w3c.dom.Text;

/**
 * Created by admin on 2016/9/22.
 */
public class SearchActivity extends BaseActvity {

    private String TAG = "search";
    private LinearLayout mHotLl;
    private TextView mHotTv;
    private ListView mHistoryLl;
    private TextView mHistoryTv;
    private LinearLayout mDeleteLl;
    private EditText mSearchEt;
    private TextView mCancelTv;
    private NoScrollGridView mGridGv;
    private LinearLayout mSearchLl;

    private FrameLayout mCarFl;
    private TextView mRedTv;
    private RelativeLayout mBgRl;

    private String[] data = new String[]{
            "可", "可口可乐会可口可乐会", "可口可乐会可口可乐会", "可口可", "会理石榴", "可口可乐"
    };

    @Override
    protected int loadLayout() {
        return R.layout.activity_search;
    }

    private LinearLayout hotll = null;
    private BgTextView textView;


    @Override
    protected void init() {
        F();

//        mGridGv.setVisibility(View.GONE);
//        mCarFl.setVisibility(View.GONE);
        mSearchLl.setVisibility(View.GONE);
        //热门搜索添加数据
        for (int i = 0; i < data.length; i++) {
            if (i % 3 == 0) {
                hotll = new LinearLayout(mContext);
                hotll.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams1.topMargin = PixelUtil.dp2px(15);
                hotll.setLayoutParams(layoutParams1);
                mHotLl.addView(hotll);
            }
            textView = new BgTextView(mContext, data[i]);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchEt.setText(((TextView) (view)).getText().toString().trim());
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = PixelUtil.dp2px(15);
            layoutParams.rightMargin = PixelUtil.dp2px(5);
            textView.setLayoutParams(layoutParams);
            hotll.addView(textView);
        }

        //历史记录添加数据
        mHistoryLl.setAdapter(new TestAdapter(5, R.layout.item_search_history_list));
        mHistoryLl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSearchEt.setText(((TextView) ((RelativeLayout) (view)).getChildAt(1)).getText().toString().trim());
            }
        });

        if (adapter == null) {
            adapter = new HistoryAdapter(10, R.layout.item_search_goods_grid, new HistoryViewHolder());
        } else {
            adapter.setSize(10);
        }
        mGridGv.setAdapter(adapter);

        setOnClicks(mDeleteLl, mCancelTv, mCarFl);
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_search_delete_ll:
                CustomDialog.Builder ibuilder = new CustomDialog.Builder(Config.currentActivity);
                ibuilder.setTitle("提示");
                ibuilder.setMessage("你确定要清除历史记录吗?");
                ibuilder.setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //删除历史记录的数据
                        mHistoryTv.setVisibility(View.GONE);
                        mHistoryLl.setVisibility(View.GONE);
                        mDeleteLl.setVisibility(View.GONE);
                    }
                });
                ibuilder.setNegativeButton("取消", null);
                CustomDialog dialog = ibuilder.create();
                dialog.setCancelable(false);
                dialog.show();
                break;
            case R.id.ay_search_cancel_tv:
                animFinsh();
                break;
            case R.id.ay_search_car_fl:
                openDataAct(MainActivity.class, "1");
                break;
        }
    }

    private HistoryAdapter adapter;

    class HistoryAdapter extends KingAdapter {

        public HistoryAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            final HistoryViewHolder viewHolder = (HistoryViewHolder) o;
            viewHolder.mCarIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addCart((ImageView) ((LinearLayout) ((view.getParent()).getParent())).getChildAt(0));
                }
            });
            SpannableString msp = new SpannableString(viewHolder.mPriceTv.getText().toString());
            msp.setSpan(new RelativeSizeSpan(0.7f), viewHolder.mPriceTv.getText().toString().indexOf(".") + 1, viewHolder.mPriceTv.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
            viewHolder.mPriceTv.setText(msp);
        }
    }

    class HistoryViewHolder {
        String TAG = "hist";
        ImageView mIconIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        ImageView mCarIv;
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
            mCarFl.getLocationInWindow(endLoc);


//        三、正式开始计算动画开始/结束的坐标
            //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
            float startX = startLoc[0] - parentLocation[0];
            float startY = startLoc[1] - parentLocation[1];

            //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
            float toX = endLoc[0] - parentLocation[0] + mCarFl.getWidth() / 5;
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
            ToastInfo("亲，您添加购物车好辛苦，休息一下");
        }

    }
}
