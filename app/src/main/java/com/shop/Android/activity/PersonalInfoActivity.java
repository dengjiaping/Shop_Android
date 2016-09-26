package com.shop.Android.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.CutActivity;
import com.shop.Android.Config;
import com.shop.Android.base.BaseActvity;
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

    @Override
    protected int loadLayout() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initTitleBar() {
        initTitle("个人资料");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        CutActivity.setCallback(new CutActivity.OnPostFile() {
            @Override
            public void onPost(File file, Activity activity) {
                activity.finish();
                GlideCircle(file, mHeaderIv);
                kingData.sendBroadCast(Config.ICON);
            }
        });
        setOnClicks(mNickRl, mNicknameRl, mCauseRl, mHeaderRl, mSexRl, mBoysRl, mGirlsRl, mOkRl, mGenderRl);
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_personal_boys_rl:
                mBoysIv.setVisibility(View.VISIBLE);
                mGirlsIv.setVisibility(View.GONE);
                gender = "0";
                break;
            case R.id.ay_personal_cause_rl:
                mNicknameRl.setVisibility(View.GONE);
                break;
            case R.id.ay_personal_girls_rl:
                mBoysIv.setVisibility(View.GONE);
                mGirlsIv.setVisibility(View.VISIBLE);
                gender = "1";
                break;
            case R.id.ay_personal_gender_rl:
                mGenderRl.setVisibility(View.GONE);
                break;
            case R.id.ay_personal_nick_rl:
                mNicknameRl.setVisibility(View.VISIBLE);
                break;
            case R.id.ay_personal_ok_rl:
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
