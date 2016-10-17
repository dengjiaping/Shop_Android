package com.shop.Android.activity;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.AddressBean;
import com.shop.Net.Bean.IntegralBean;
import com.shop.Net.Param.Token;
import com.shop.R;

/**
 * Created by admin on 2016/9/21.
 */
public class IntegralActivity extends BaseActvity {
    private String TAG = "integral";
    private AnimNoLineRefreshListView mListRv;
    private ImageView mImgIv;
    private TextView mNumTv;
    private TextView mGradeTv;
    private IntegralAdapter integralAdapter;
    private IntegralBean integralBean;

    @Override
    protected int loadLayout() {
        return R.layout.activity_integral;
    }

    @Override
    protected void initTitleBar() {
        initTitle("积分等级");
        mTitleLeftIv.setImageResource(R.mipmap.back);
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
    }

    @Override
    protected void init() {
        F();
        mListRv.setPullLoadEnable(false);
        Post(ActionKey.INTEGRAL,new Token(), IntegralBean.class);

        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListRv.onRefreshComplete();
                    }
                },1000);
            }

            @Override
            public void onLoadMore() {

            }
        });

    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.INTEGRAL:
               integralBean = (IntegralBean) result;
                if (integralBean.getCode()==200){
                    integralAdapter = new IntegralAdapter(integralBean.getData().getOrder_integrals().size(), R.layout.activity_integral_item, new IntegralViewHolder());
                    mListRv.setAdapter(integralAdapter);
                    mNumTv.setText(integralBean.getData().getIntegral());
                    mGradeTv.setText(integralBean.getData().getGrade());
                    GlideCircle(integralBean.getData().getPoster(),mImgIv);
                }else if (integralBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                     ToastInfo(integralBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {

    }

    class IntegralAdapter extends KingAdapter {

        public IntegralAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            IntegralViewHolder viewHolder = (IntegralViewHolder) o;
            viewHolder.mNumTv.setText(integralBean.getData().getOrder_integrals().get(i).getOrder_number());
            viewHolder.mAddTv.setText("+"+integralBean.getData().getOrder_integrals().get(i).getNum());
            viewHolder.mTimeTv.setText(integralBean.getData().getOrder_integrals().get(i).getCreated_time());
        }
    }

    class IntegralViewHolder {
        String TAG = "integrals";
        TextView mNumTv;
        TextView mTimeTv;
        TextView mAddTv;
    }
}
