package com.shop.Android.fragment;

import android.widget.ImageView;

import com.shop.Android.activity.MsgActivity;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.base.BaseFragment;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class MineFragment extends BaseFragment {

    private String TAG = "mine";
    private ImageView mLeftIv;
    private ImageView mIconIv;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        F();
        setOnClicks(mLeftIv, mIconIv);
    }


    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ft_mine_left_iv:
                openActivity(MsgActivity.class);
                break;
            case R.id.ft_mine_icon_iv:
                break;
        }

    }
}
