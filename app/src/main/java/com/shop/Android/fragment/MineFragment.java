package com.shop.Android.fragment;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shop.Android.activity.MineEvaluateActivity;
import com.shop.Android.activity.MsgActivity;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.widget.MineView;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class MineFragment extends BaseFragment {

    private String TAG = "mine";
    private ImageView mLeftIv;
    private ImageView mIconIv;
    private MineView mEvaluateMv;
    private MineView mCollectMv;
    private MineView mIntegralMv;
    private MineView mAddressMv;
    private MineView mShareMv;
    private MineView mOpinionMv;
    private MineView mHelpMv;
    private MineView mSetMv;
    private LinearLayout mContactLl;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        F();
        setOnClicks(mLeftIv, mIconIv,mAddressMv,mCollectMv,mContactLl,mIntegralMv,mEvaluateMv,mHelpMv,mOpinionMv,mSetMv,mShareMv);
    }


    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ft_mine_left_iv:
                openActivity(MsgActivity.class);
                break;
            case R.id.ft_mine_icon_iv:
                break;
            case R.id.ft_mine_address_mv:
                break;
            case R.id.ft_mine_collect_mv:
                break;
            case R.id.ft_mine_contact_ll:
                break;
            case R.id.ft_mine_evaluate_mv:
                openActivity(MineEvaluateActivity.class);
                break;
            case R.id.ft_mine_help_mv:
                break;
            case R.id.ft_mine_integral_mv:
                break;
            case R.id.ft_mine_opinion_mv:
                break;
            case R.id.ft_mine_set_mv:
                break;
            case R.id.ft_mine_share_mv:
                break;
        }

    }
}
