package com.xiaojishop.Android.fragment;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.king.Base.KingData;
import com.king.Utils.GsonUtil;
import com.king.Utils.SPrefUtil;
import com.xiaojishop.Android.Config;
import com.xiaojishop.Android.DataKey;
import com.xiaojishop.Android.activity.AddressManagerActivity;
import com.xiaojishop.Android.activity.IntegralActivity;
import com.xiaojishop.Android.activity.MineCollectActivity;
import com.xiaojishop.Android.activity.MineEvaluateActivity;
import com.xiaojishop.Android.activity.MsgActivity;
import com.xiaojishop.Android.activity.PersonalInfoActivity;
import com.xiaojishop.Android.activity.SetActivity;
import com.xiaojishop.Android.activity.ShareActivity;
import com.xiaojishop.Android.activity.SuggestionFeedbackActivity;
import com.xiaojishop.Android.activity.UserHelperActivity;
import com.xiaojishop.Android.base.BaseFragment;
import com.xiaojishop.Android.widget.MineView;
import com.xiaojishop.Net.Bean.UserBean;
import com.xiaojishop.R;

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
    private TextView mNameTv;
    private UserBean userBean;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        F();
        userBean = ((UserBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(DataKey.USER, ""), UserBean.class));
        if (userBean == null) {
            mIconIv.setImageResource(R.mipmap.default_hader);
            mNameTv.setText("e闪生活购");
        } else {
            GlideCircle(userBean.getData().getUser_info().getPoster(), mIconIv);
            mNameTv.setText(userBean.getData().getUser_info().getNick_name());
        }
        setOnClicks(mLeftIv, mIconIv, mAddressMv, mCollectMv, mContactLl, mIntegralMv, mEvaluateMv, mHelpMv, mOpinionMv, mSetMv, mShareMv);
    }

    @Override
    public void onResume() {
        super.onResume();
        kingData.registerWatcher(Config.ICON, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                userBean = ((UserBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(DataKey.USER, ""), UserBean.class));
                GlideCircle(userBean.getData().getUser_info().getPoster(), mIconIv);
                mNameTv.setText(userBean.getData().getUser_info().getNick_name());
            }
        });
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
                callPhone(mPhoneTv.getText().toString(), mContext);
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
