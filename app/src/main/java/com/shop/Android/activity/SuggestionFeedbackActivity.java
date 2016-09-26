package com.shop.Android.activity;

import android.widget.EditText;
import android.widget.RelativeLayout;

import com.shop.Android.base.BaseActvity;
import com.shop.R;

/**
 * Created by admin on 2016/9/23.
 */
public class SuggestionFeedbackActivity extends BaseActvity {
    private String TAG = "suggestion";
    private EditText mContentEt;
    private EditText mPhoneEt;
    private RelativeLayout mSubmitRl;
    @Override
    protected int loadLayout() {
        return R.layout.activity_suggestion_feedback;
    }

    @Override
    protected void initTitleBar() {
        initTitle("意见反馈");
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        setOnClicks(mSubmitRl);
    }

    @Override
    protected void onClickSet(int i) {
     switch (i){
         case R.id.ay_suggestion_submit_rl:

             break;
     }
    }
}
