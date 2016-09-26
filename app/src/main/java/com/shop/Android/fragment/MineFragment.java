package com.shop.Android.fragment;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.king.Base.KingData;
import com.shop.Android.Config;
import com.shop.Android.activity.AddressManagerActivity;
import com.shop.Android.activity.IntegralActivity;
import com.shop.Android.activity.MineCollectActivity;
import com.shop.Android.activity.MineEvaluateActivity;
import com.shop.Android.activity.MsgActivity;
import com.shop.Android.activity.PersonalInfoActivity;
import com.shop.Android.activity.SetActivity;
import com.shop.Android.activity.ShareActivity;
import com.shop.Android.activity.SuggestionFeedbackActivity;
import com.shop.Android.activity.UserHelperActivity;
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
    private TextView mPhoneTv;
    @Override
    protected int loadLayout() {
        return R.layout.fragment_mine;
    }
    @Override
    protected void init() {
        F();
        kingData.registerWatcher(Config.ICON, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                GlideCircle("http://img.firefoxchina.cn/2016/09/5/201609230915030.jpg",mIconIv);
            }
        });
        setOnClicks(mLeftIv, mIconIv,mAddressMv,mCollectMv,mContactLl,mIntegralMv,mEvaluateMv,mHelpMv,mOpinionMv,mSetMv,mShareMv);
    }


    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ft_mine_left_iv:
                openActivity(MsgActivity.class);
                break;
            case R.id.ft_mine_icon_iv:
                openActivity(PersonalInfoActivity.class);
                break;
            case R.id.ft_mine_address_mv:
                openActivity(AddressManagerActivity.class);
                break;
            case R.id.ft_mine_collect_mv:
                openActivity(MineCollectActivity.class);
                break;
            case R.id.ft_mine_contact_ll:

                break;
            case R.id.ft_mine_evaluate_mv:
                openActivity(MineEvaluateActivity.class);
                break;
            case R.id.ft_mine_help_mv:
                openActivity(UserHelperActivity.class);
                break;
            case R.id.ft_mine_integral_mv:
                openActivity(IntegralActivity.class);
                break;
            case R.id.ft_mine_opinion_mv:
                openActivity(SuggestionFeedbackActivity.class);
                break;
            case R.id.ft_mine_set_mv:
                openActivity(SetActivity.class);
                break;
            case R.id.ft_mine_share_mv:
                openActivity(ShareActivity.class);
                break;
        }

    }
}
