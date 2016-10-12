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
        integralAdapter = new IntegralAdapter(3, R.layout.activity_integral_item, new IntegralViewHolder());
        mListRv.setAdapter(integralAdapter);
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
    protected void onClickSet(int i) {

    }

    class IntegralAdapter extends KingAdapter {

        public IntegralAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {

        }
    }

    class IntegralViewHolder {
        String TAG = "integrals";
        TextView mOrderTv;
        TextView mTimeTv;
        TextView mAddTv;
    }
}
