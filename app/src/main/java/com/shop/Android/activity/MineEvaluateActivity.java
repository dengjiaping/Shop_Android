package com.shop.Android.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Internet.user_method.CallServer;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.EvaluateListBean;
import com.shop.Net.Param.Token;
import com.shop.R;

/**
 * Created by admin on 2016/9/27.
 */
public class MineEvaluateActivity extends BaseActvity {
    private String TAG = "evaluate";
    private AnimNoLineRefreshListView mListRv;
    private GoodsEvaluateAdapter goodsEvaluateAdapter;
    private EvaluateListBean evaluateListBean;


    @Override
    protected int loadLayout() {
        return R.layout.activity_mine_evaluate;
    }

    @Override
    protected void initTitleBar() {
        initTitle("我的评价");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        Post(ActionKey.EVALUATE_MINE,new Token(), EvaluateListBean.class);
        mListRv.setPullLoadEnable(false);
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.EVALUATE_MINE,new Token(), EvaluateListBean.class);
            }

            @Override
            public void onLoadMore() {

            }
        });

    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.EVALUATE_MINE:
                evaluateListBean = (EvaluateListBean) result;
                if (evaluateListBean.getCode()==200){
                    goodsEvaluateAdapter = new GoodsEvaluateAdapter(evaluateListBean.getData().size(),R.layout.activity_mine_evaluate_item,new GoodsEvaluateViewHolder());
                    mListRv.setAdapter(goodsEvaluateAdapter);
                }else if (2001==evaluateListBean.getCode()){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(evaluateListBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {

    }

    class GoodsEvaluateAdapter extends KingAdapter{

        public GoodsEvaluateAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            GoodsEvaluateViewHolder viewHolder = (GoodsEvaluateViewHolder) o;
            viewHolder.mNameTv.setText(evaluateListBean.getData().get(i).getShop_name());
            viewHolder.mTimeTv.setText(evaluateListBean.getData().get(i).getCreated_time());
            viewHolder.mNumberTv.setText(evaluateListBean.getData().get(i).getOrder_number());
            switch (evaluateListBean.getData().get(i).getType()){
                case 1:
                    viewHolder.mAllTv.setText("好评");
                    break;
                case 2:
                    viewHolder.mAllTv.setText("中评");
                    break;
                case 3:
                    viewHolder.mAllTv.setText("差评");
                    break;
            }
        }
    }

    class  GoodsEvaluateViewHolder{
        String TAG = "mine";
        ImageView mGoodsIv;
        TextView mNameTv;
        TextView mNumberTv;
        TextView mAllTv;
        TextView mEvaluateTv;
        TextView mTimeTv;
    }
}
