package com.xiaojishop.Android.activity;

import android.widget.EditText;
import android.widget.RelativeLayout;

import com.king.Utils.CheckUtil;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Net.ActionKey;
import com.xiaojishop.Net.Bean.BaseBean;
import com.xiaojishop.Net.Param.SuggestionParam;
import com.xiaojishop.R;

/**
 * Created by admin on 2016/9/23.
 */
public class SuggestionFeedbackActivity extends BaseActvity {
    private String TAG = "suggestion";
    private EditText mContentEt;
    private EditText mPhoneEt;
    private RelativeLayout mSubmitRl;
    private String phone;
    private String content;

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
        switch (i) {
            case R.id.ay_suggestion_submit_rl:
                submitData();
                break;
        }
    }

    private void submitData() {
        phone = mPhoneEt.getText().toString().trim();
        content = mContentEt.getText().toString().trim();
        if (!content.equals("") && CheckUtil.isPhone(mPhoneEt)) {
            Post(ActionKey.ADD_SUGGESTION, new SuggestionParam(phone, content), BaseBean.class);
        } else {
            ToastInfo("反馈内容不能为空或手机号码不能为空");
        }
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.ADD_SUGGESTION:
                BaseBean bean = (BaseBean) result;
                if (bean.getCode() == 200) {
                    ToastInfo("提交成功");
                    animFinsh();
                } else if (bean.getCode() == 2001) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(bean.getMsg());
                }
                break;
        }
    }
}
