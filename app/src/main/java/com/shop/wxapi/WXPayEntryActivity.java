package com.shop.wxapi;


import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.king.Base.KingData;
import com.king.Utils.DateUtil;
import com.king.Utils.GsonUtil;
import com.king.Utils.SPrefUtil;
import com.king.Utils.ToastUtil;
import com.shop.Android.Config;
import com.shop.Android.DataKey;
import com.shop.Android.SPKey;
import com.shop.Android.activity.LoginActivity;
import com.shop.Android.activity.MainActivity;
import com.shop.Android.activity.OrderDetailsActivity;
import com.shop.Android.alipy.PayResult;
import com.shop.Android.base.BaseActvity;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.AlipayBean;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.IndexBean;
import com.shop.Net.Bean.WeixinBean;
import com.shop.Net.Param.Pay;
import com.shop.R;
import com.shop.ShopCar.ShopCar;
import com.shop.ShopCar.TMShopCar;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/8/19.
 */
public class WXPayEntryActivity extends BaseActvity implements IWXAPIEventHandler {

    private String TAG = "pay";
    private CheckBox mWeixinCb;
    private CheckBox mAlipayCb;
    private TextView mPriceTv;
    private TextView mOrderTv;
    private TextView mNumberTv;
    private RelativeLayout mWeixinRl;
    private RelativeLayout mAlipayRl;
    private CheckBox mHdfkCb;
    private RelativeLayout mHdfkRl;

    private int state = 0;


    @Override
    protected int loadLayout() {
        return R.layout.activity_pay;
    }

    private CountDownTimer timer;
    private long mmSec = 0;
    private TextView mHourTv;
    private TextView mMinTv;
    private TextView mSecTv;

    @Override
    protected void init() {
        initTitle("支付订单");
        F();

        msgApi = WXAPIFactory.createWXAPI(WXPayEntryActivity.this, null);
        kingData.registerWatcher("ZZREFRESHPAY", new KingData.KingCallBack() {
            @Override
            public void onChange() {
                mPriceTv.setText(kingData.getData(DataKey.PRICE));
                mOrderTv.setText("确认支付" + kingData.getData(DataKey.PRICE));
                mNumberTv.setText(((IndexBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX, ""), IndexBean.class)).getData().getShop().getName());

                if (timer == null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date d1 = sdf.parse(kingData.getData(DataKey.TIME));
                        Date d2 = sdf.parse(DateUtil.getYMD(System.currentTimeMillis()) + " " + DateUtil.getHMS(System.currentTimeMillis()));
                        long dd1 = d1.getTime();
                        long dd2 = d2.getTime();
                        mmSec = dd1 - dd2;

                        mHourTv.setText((mmSec / 3600 / 1000 + "").length() == 1 ? ("0" + mmSec / 3600 / 1000) : (mmSec / 3600 / 1000 + ""));
                        mMinTv.setText((mmSec / 60 / 1000 % 60 + "").length() == 1 ? ("0" + mmSec / 60 / 1000 % 60) : (mmSec / 60 / 1000 % 60 + ""));
                        mSecTv.setText((mmSec / 1000 % 60 + "").length() == 1 ? ("0" + mmSec / 1000 % 60) : (mmSec / 1000 % 60 + ""));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    timer = new CountDownTimer(mmSec, 1000) {

                        @Override
                        public void onTick(long l) {
                            mmSec = mmSec - 1000;
                            mHourTv.setText((mmSec / 3600 / 1000 + "").length() == 1 ? ("0" + mmSec / 3600 / 1000) : (mmSec / 3600 / 1000 + ""));
                            mMinTv.setText((mmSec / 60 / 1000 % 60 + "").length() == 1 ? ("0" + mmSec / 60 / 1000 % 60) : (mmSec / 60 / 1000 % 60 + ""));
                            mSecTv.setText((mmSec / 1000 % 60 + "").length() == 1 ? ("0" + mmSec / 1000 % 60) : (mmSec / 1000 % 60 + ""));
                        }

                        @Override
                        public void onFinish() {

                        }
                    };
                    timer.start();
                }
            }
        });

        mWeixinCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWeixinCb.setChecked(true);
                mAlipayCb.setChecked(false);
                mHdfkCb.setChecked(false);
            }
        });

        mAlipayCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlipayCb.setChecked(true);
                mWeixinCb.setChecked(false);
                mHdfkCb.setChecked(false);
            }
        });

        mHdfkCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlipayCb.setChecked(false);
                mWeixinCb.setChecked(false);
                mHdfkCb.setChecked(true);
            }
        });


        setOnClicks(mOrderTv, mWeixinRl, mAlipayRl, mHdfkRl);


    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_pay_order_tv:
                if (mWeixinCb.isChecked()) {
                    Post(ActionKey.PAY, new Pay(kingData.getData(DataKey.ID), "2"), WeixinBean.class);
                } else if (mAlipayCb.isChecked()) {
                    Post(ActionKey.PAY, new Pay(kingData.getData(DataKey.ID), "1"), AlipayBean.class);
                } else if (mHdfkCb.isChecked()) {
                    Post(ActionKey.PAY, new Pay(kingData.getData(DataKey.ID), "3"), BaseBean.class);
                }
                break;
            case R.id.ay_pay_alipay_rl:
                mAlipayCb.setChecked(true);
                mWeixinCb.setChecked(false);
                mHdfkCb.setChecked(false);
                break;
            case R.id.ay_pay_weixin_rl:
                mWeixinCb.setChecked(true);
                mAlipayCb.setChecked(false);
                mHdfkCb.setChecked(false);
                break;
            case R.id.ay_pay_hdfk_rl:
                mWeixinCb.setChecked(false);
                mAlipayCb.setChecked(false);
                mHdfkCb.setChecked(true);
                break;


        }

    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.PAY:
                if (mWeixinCb.isChecked()) {
                    WeixinBean weixinBean = (WeixinBean) result;
                    if (weixinBean.getCode() == 200) {
                        WeixinPay(weixinBean);
                    } else if (weixinBean.getCode() == 2001) {
                        ToastInfo(weixinBean.getMsg());
                        openActivity(LoginActivity.class);
                    }
                } else if (mAlipayCb.isChecked()) {
                    AlipayBean alipayBean = (AlipayBean) result;
                    if (alipayBean.getCode() == 200) {
                        AlipayPay(alipayBean);
                    } else if (alipayBean.getCode() == 2001) {
                        ToastInfo(alipayBean.getMsg());
                        openActivity(LoginActivity.class);
                    }
                } else if (mHdfkCb.isChecked()) {
                    BaseBean baseBean = (BaseBean) result;
                    if (baseBean.getCode() == 200) {
                        ToastInfo("提交成功");
                        switch (kingData.getData(DataKey.TYPE)) {
                            case "0":
                                TMShopCar.ClearSelf();
                                break;
                            case "1":
                                ShopCar.ClearSelf();
                                break;
                        }
                        kingData.sendBroadCast("CAR");
                        animFinsh();
                        kingData.putData(DataKey.ORDER, kingData.getData(DataKey.ID));
                        kingData.sendBroadCast(Config.ORDER_ID);
                        openActivity(OrderDetailsActivity.class);
                    } else if (baseBean.getCode() == 2001) {
                        ToastInfo(baseBean.getMsg());
                        openActivity(LoginActivity.class);
                    } else {
                        ToastInfo(baseBean.getMsg());
                    }
                }

                break;
        }
    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtil.showToast("支付成功");
                        switch (kingData.getData(DataKey.TYPE)) {
                            case "0":
                                TMShopCar.ClearSelf();
                                break;
                            case "1":
                                ShopCar.ClearSelf();
                                break;
                        }
                        kingData.sendBroadCast("CAR");
                        animFinsh();
                        kingData.putData(DataKey.ORDER, kingData.getData(DataKey.ID));
                        kingData.sendBroadCast(Config.ORDER_ID);
                        openActivity(OrderDetailsActivity.class);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtil.showToast("支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtil.showToast("支付失败");
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    ToastUtil.showToast("检查结果为：" + msg.obj);
                    break;
                }
                default:
                    break;
            }
        }


    };

    private void AlipayPay(AlipayBean alipayBean) {
        final String payInfo = alipayBean.getData().getJava();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {

                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private IWXAPI msgApi;
    public static String appId;

    private void WeixinPay(WeixinBean weixinBean) {
        msgApi.handleIntent(getIntent(), WXPayEntryActivity.this);
        // 将该app注册到微信
        msgApi.registerApp(weixinBean.getData().getAppid());
        appId = weixinBean.getData().getAppid();
        sendBroadcast(new Intent("com.tencent.mm.plugin.openapi.Intent.ACTION_REFRESH_WXAPP"));
        PayReq request = new PayReq();
        request.appId = weixinBean.getData().getAppid();
        request.partnerId = weixinBean.getData().getPartnerid();
        request.prepayId = weixinBean.getData().getPrepayid();
        request.packageValue = weixinBean.getData().getPackageX();
        request.nonceStr = weixinBean.getData().getNoncestr();
        request.timeStamp = weixinBean.getData().getTimestamp();
        request.sign = weixinBean.getData().getSign();
        ToastInfo("获取订单中...");
        msgApi.sendReq(request);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        msgApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                ToastInfo("支付成功");
                switch (kingData.getData(DataKey.TYPE)) {
                    case "0":
                        TMShopCar.ClearSelf();
                        break;
                    case "1":
                        ShopCar.ClearSelf();
                        break;
                }
                kingData.sendBroadCast("CAR");
                animFinsh();
                kingData.putData(DataKey.ORDER, kingData.getData(DataKey.ID));
                kingData.sendBroadCast(Config.ORDER_ID);
                openActivity(OrderDetailsActivity.class);
            } else if (baseResp.errCode == -1) {
                ToastInfo("支付失败");
            } else if (baseResp.errCode == -2) {
                ToastInfo("取消支付");
            }
        }
    }
}
