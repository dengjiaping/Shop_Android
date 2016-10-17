package com.shop.Android.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.EvaluateBean;
import com.shop.Net.Param.OrderDetailsParam;
import com.shop.Net.Param.SubmitEvaluateParam;
import com.shop.R;

/**
 * Created by admin on 2016/9/20.
 */
public class MineGoodsEvaluateActivity extends BaseActvity {
    private String TAG = "evaluate";
    private AnimNoLineRefreshListView mListVn;
    private MineEvaluateAdapter mineEvaluateAdapter;
    private String id;
    private EvaluateBean evaluateBean;
    private String type ;


    @Override
    protected int loadLayout() {
        id = getIntent().getStringExtra("id");
        return R.layout.activity_evaluate_goods;
    }

    @Override
    protected void initTitleBar() {
        initTitle("商品评价");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        Post(ActionKey.EVALUATE_INDEXT, new OrderDetailsParam(id), EvaluateBean.class);
        mListVn.setPullLoadEnable(false);
        mListVn.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.EVALUATE_INDEXT, new OrderDetailsParam(id), EvaluateBean.class);
            }

            @Override
            public void onLoadMore() {

            }
        });

    }

    @Override
    public void onSuccess(String what, Object result) {
        mListVn.onLoadComplete();
        mListVn.onRefreshComplete();
        switch (what) {
            case ActionKey.EVALUATE_INDEXT:
                evaluateBean = (EvaluateBean) result;
                if (evaluateBean.getCode() == 200) {
                    mineEvaluateAdapter = new MineEvaluateAdapter(evaluateBean.getData().size(), R.layout.activiy_evaluate_goods_item, new EvaluateViewHolder());
                    mListVn.setAdapter(mineEvaluateAdapter);
                } else if (evaluateBean.getCode() == 2001) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(evaluateBean.getMsg());
                }
                break;
            case ActionKey.SUBMIT_GOODS:
                BaseBean baseBean = (BaseBean) result;
                if (baseBean.getCode()==200){
                    ToastInfo("提交成功");
                    animFinsh();
                }else if (baseBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(baseBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {

        }
    }

    class MineEvaluateAdapter extends KingAdapter {

        public MineEvaluateAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(final int i, Object o) {
            final EvaluateViewHolder viewHolder = (EvaluateViewHolder) o;
            viewHolder.mNameTv.setText(evaluateBean.getData().get(i).getContent());
            viewHolder.mPriceTv.setText("￥"+evaluateBean.getData().get(i).getGoods_price());
            viewHolder.mContentTv.setText(evaluateBean.getData().get(i).getGoods_subtitle());
            Glide(evaluateBean.getData().get(i).getGoods_image(),viewHolder.mImgIv);
            switch (Integer.valueOf(evaluateBean.getData().get(i).getType())){
                case 1:
                    viewHolder.mPraiseRb.setChecked(true);
                    break;
                case 2:
                    viewHolder.mMiddleRb.setChecked(true);
                    break;
                case 3:
                    viewHolder.mBadRb.setChecked(true);
                    break;

            }

            viewHolder.mBadRb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    type = "3";
                }
            });
            viewHolder.mMiddleRb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    type = "2";
                }
            });
            viewHolder.mPraiseRb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    type = "1";
                }
            });
            if (evaluateBean.getData().get(i).getType().equals("0")){
                viewHolder.mSubmitTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String content = viewHolder.mFeelEt.getText().toString().trim();
                        Post(ActionKey.SUBMIT_GOODS,new SubmitEvaluateParam(evaluateBean.getData().get(i).getId(),type,content), BaseBean.class);
                    }
                });
            }else {
                viewHolder.mSubmitTv.setBackgroundResource(R.drawable.order_btn);
                viewHolder.mSubmitTv.setTextColor(Color(R.color.color_888));
            }

        }

    }

    class EvaluateViewHolder {
        String TAG = "evaluate";
        TextView mSubmitTv;
        TextView mPriceTv;
        TextView mContentTv;
        RadioButton mPraiseRb;
        RadioButton mMiddleRb;
        RadioButton mBadRb;
        TextView mNameTv;
        EditText mFeelEt;
        ImageView mImgIv;


    }
}