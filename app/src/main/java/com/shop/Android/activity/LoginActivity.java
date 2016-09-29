package com.shop.Android.activity;

import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

import com.king.Utils.CheckUtil;
import com.king.Utils.GsonUtil;
import com.shop.Android.DataKey;
import com.shop.Android.base.BaseActvity;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.UserBean;
import com.shop.Net.Param.CodeParam;
import com.shop.Net.Param.LoginParam;
import com.shop.R;

/**
 * Created by admin on 2016/9/29.
 */
public class LoginActivity extends BaseActvity {

    private String TAG = "login";
    private EditText mUserEt;
    private EditText mPassEt;
    private TextView mCodeTv;
    private TextView mLoginTv;


    @Override
    protected int loadLayout() {
        NoSlide();
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        F();

        setOnClicks(mCodeTv, mLoginTv);
    }

    private String tempPhone = "";

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_login_code_tv:
                if (tempPhone.equals(getText(mUserEt)) && !tempPhone.isEmpty()) {
                    if (mCodeTv.equals("发送验证码")) {
                        if (CheckUtil.isPhone(mUserEt)) {
                            Post(ActionKey.CODE, new CodeParam(getText(mUserEt)), BaseBean.class);
                        }
                    } else {
                        ToastInfo("短信正在路上，请稍后");
                    }
                } else {
                    tempPhone = getText(mUserEt);
                    if (CheckUtil.isPhone(mUserEt)) {
                        Post(ActionKey.CODE, new CodeParam(getText(mUserEt)), BaseBean.class);
                    }
                }
                break;
            case R.id.ay_login_login_tv:
                openActivity(MainActivity.class);
                if (!isInputError()) {
                    Post(ActionKey.LOGIN, new LoginParam(getText(mPassEt), getText(mUserEt)), BaseBean.class);
                }
                break;
        }
    }

    public boolean isInputError() {
        if (!CheckUtil.isPhone(mUserEt)) {
            return true;
        } else if (getText(mPassEt).isEmpty()) {
            ToastInfo("请输入验证码");
            return true;
        }
        return false;
    }

    private int count = 60000;
    private CountDownTimer timer;

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.CODE:
                BaseBean bean = (BaseBean) result;
                if (bean.getCode() == 200) {
                    if (timer != null) {
                        timer.cancel();
                        count = 60000;
                    }
                    timer = new CountDownTimer(60000, 1000) {

                        @Override
                        public void onTick(long l) {
                            count = count - 1000;
                            mCodeTv.setText("重新获取(" + (count / 1000) + ")");
                        }

                        @Override
                        public void onFinish() {
                            count = 60000;
                            mCodeTv.setText("发送验证码");
                            tempPhone = "";
                        }
                    };
                    timer.start();
                } else {
                    ToastInfo(bean.getMsg());
                }
                break;
            case ActionKey.LOGIN:
                UserBean userBean = (UserBean) result;
                if (userBean.getCode() == 200) {
                    kingData.putData(DataKey.USER, GsonUtil.Bean2Str(userBean));
                } else {
                    ToastInfo(userBean.getMsg());
                }
                break;
        }
    }
}
