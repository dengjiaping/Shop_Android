package com.shop.Android.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.CutActivity;
import com.king.Utils.GsonUtil;
import com.king.Utils.SPrefUtil;
import com.shop.Android.Config;
import com.shop.Android.DataKey;
import com.shop.Android.base.BaseActvity;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.EditUserInfoBean;
import com.shop.Net.Bean.UserBean;
import com.shop.Net.Param.HearderParam;
import com.shop.R;

import java.io.File;

/**
 * Created by admin on 2016/9/23.
 */
public class PersonalInfoActivity extends BaseActvity {
    private String TAG = "personal";
    private ImageView mHeaderIv;
    private RelativeLayout mHeaderRl;
    private TextView mNicknameTv;
    private RelativeLayout mNickRl;
    private RelativeLayout mNicknameRl;
    private EditText mNicknameEt;
    private TextView mPhoneTv;
    private TextView mSexTv;
    private RelativeLayout mSexRl;
    private RelativeLayout mGenderRl;
    private TextView mBoysTv;
    private ImageView mBoysIv;
    private ImageView mGirlsIv;
    private TextView mGirlsTv;
    private RelativeLayout mOkRl;
    private RelativeLayout mCauseRl;
    private RelativeLayout mBoysRl;
    private RelativeLayout mGirlsRl;
    private String gender;
    private File poster;
    private UserBean userBean;
    @Override
    protected int loadLayout() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initTitleBar() {
        initTitle("个人资料");
        mTitleLeftIv.setImageResource(R.mipmap.back);
        mTitleRightTv.setText("保存");
        mTitleRightTv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CutActivity.setCallback(new CutActivity.OnPostFile() {
            @Override
            public void onPost(File file, Activity activity) {
                poster = file;
                GlideCircle(file.getAbsolutePath(), mHeaderIv);
                activity.finish();
            }
        });
    }

    @Override
    protected void init() {
        F();
        userBean= ((UserBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(DataKey.USER,""), UserBean.class));
        if (userBean!=null){
            mNicknameTv.setText(userBean.getData().getUser_info().getNick_name());
            mNicknameEt.setText(userBean.getData().getUser_info().getNick_name());
            GlideCircle(userBean.getData().getUser_info().getPoster(),mHeaderIv);
            mPhoneTv.setText(userBean.getData().getUser_info().getPhone());
            if (userBean.getData().getUser_info().getGender().equals("0")){
                mSexTv.setText("女");
                mGirlsIv.setVisibility(View.VISIBLE);
                mBoysIv.setVisibility(View.GONE);
            }else {
                mSexTv.setText("男");
                mGirlsIv.setVisibility(View.GONE);
                mBoysIv.setVisibility(View.VISIBLE);
            }
        }
        setOnClicks(mNickRl, mNicknameRl, mCauseRl, mHeaderRl, mSexRl, mBoysRl, mGirlsRl, mOkRl, mGenderRl);
        mTitleRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNicknameEt.getText().toString().trim();
                Post(ActionKey.EDIT_USER, new HearderParam( gender,name, poster), EditUserInfoBean.class);
            }
        });
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.EDIT_USER:
                EditUserInfoBean bean = (EditUserInfoBean) result;
                if (bean.getCode()==200){
                    ToastInfo("修改成功");
                    userBean.getData().getUser_info().setPoster(bean.getData().getPoster());
                    userBean.getData().getUser_info().setGender(bean.getData().getGender());
                    userBean.getData().getUser_info().setNick_name(bean.getData().getNick_name());
                    SPrefUtil.Function.putData(DataKey.USER, GsonUtil.Bean2Str(userBean));
                    kingData.sendBroadCast(Config.ICON);
                    animFinsh();
                }else  if (bean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(bean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_personal_boys_rl:
                mBoysIv.setVisibility(View.VISIBLE);
                mGirlsIv.setVisibility(View.GONE);
                gender = "1";
                break;
            case R.id.ay_personal_cause_rl:
                mNicknameTv.setText(mNicknameEt.getText().toString().trim());
                mNicknameRl.setVisibility(View.GONE);
                break;
            case R.id.ay_personal_girls_rl:
                mBoysIv.setVisibility(View.GONE);
                mGirlsIv.setVisibility(View.VISIBLE);
                gender = "0";
                break;
            case R.id.ay_personal_gender_rl:
                mGenderRl.setVisibility(View.GONE);
                break;
            case R.id.ay_personal_nick_rl:
                mNicknameRl.setVisibility(View.VISIBLE);
                break;
            case R.id.ay_personal_ok_rl:
                if (gender.equals("0")){
                    mSexTv.setText("女");
                }else {
                    mSexTv.setText("男");
                }
                mGenderRl.setVisibility(View.GONE);
                break;
            case R.id.ay_personal_sex_rl:
                mGenderRl.setVisibility(View.VISIBLE);
                break;
            case R.id.ay_personal_nickname_rl:
                mNicknameRl.setVisibility(View.GONE);
                break;
            case R.id.ay_personal_header_rl:
                openSingleCutPic(mContext);
                break;
        }
    }
}
