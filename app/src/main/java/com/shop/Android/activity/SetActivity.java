package com.shop.Android.activity;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Dialog.CustomDialog;
import com.king.DownloadManager.VersionUpdateUtils;
import com.king.DownloadManager.onProgressListener;
import com.king.Utils.SPrefUtil;
import com.shop.Android.DataKey;
import com.shop.Android.base.BaseActvity;
import com.shop.R;
import com.shop.Utils.DataCleanManager;

/**
 * Created by admin on 2016/9/23.
 */
public class SetActivity extends BaseActvity {
    private String TAG = "set";
    private RelativeLayout mCacheRl;
    private RelativeLayout mVersionRl;
    private RelativeLayout mAboutRl;
    private RelativeLayout mServiceRl;
    private RelativeLayout mExitRl;
    private TextView mCacheTv;
    private TextView mVersionTv;
    private LinearLayout mDialogLl;
    private ImageView mIconIv;
    private TextView mMessageTv;
    private AnimationDrawable FlyDrawable;
    boolean show = false;

    @Override
    protected int loadLayout() {
        return R.layout.activity_set;
    }

    @Override
    protected void initTitleBar() {
        initTitle("设置");
        mTitleLeftIv.setImageResource(R.mipmap.back);

    }

    @Override
    protected void init() {
        F();
        FlyDrawable = (AnimationDrawable) mIconIv.getDrawable();

        try {
            mCacheTv.setText(DataCleanManager.getTotalCacheSize(SetActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setOnClicks(mCacheRl, mVersionRl, mAboutRl, mServiceRl, mExitRl,mDialogLl);
        mTitleLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animFinsh();
                mDialogLl.setVisibility(View.GONE);
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    showLoading(show);
                    mDialogLl.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mDialogLl.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void showLoading(boolean show) {
        if (FlyDrawable != null) {
            FlyDrawable.start();
        }
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_set_cache_rl:
                DataCleanManager.clearAllCache(SetActivity.this);
                try {
                    mCacheTv.setText(DataCleanManager.getTotalCacheSize(SetActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ay_set_about_rl:
                openActivity(UserAgreementActivity.class);
                break;
            case R.id.ay_set_version_rl:
                CustomDialog.Builder ibuilder = new CustomDialog.Builder(mContext);
                ibuilder.setTitle("版本信息");
                ibuilder.setMessage("当前是最新版本");
                ibuilder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        VersionUpdateUtils.download("http://eshop.ittapp.com/apk/app-debug.apk", new onProgressListener() {
                            @Override
                            public void onChange(int i) {
                                if (i < 100) {
                                    mHandler.sendEmptyMessage(0);
                                } else {
                                    mHandler.sendEmptyMessage(1);
                                }

                            }
                        });

                    }
                });
                ibuilder.setNegativeButton("取消", null);
                CustomDialog dialog = ibuilder.create();
                dialog.setCancelable(false);
                dialog.show();
                break;
            case R.id.ay_set_service_rl:
                openActivity(UserServiceActivity.class);
                break;
            case R.id.ay_set_exit_rl:
                CustomDialog.Builder ibuilder1 = new CustomDialog.Builder(mContext);
                ibuilder1.setTitle("退出");
                ibuilder1.setMessage("您忍心离开吗？");
                ibuilder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SPrefUtil.Function.removeData(DataKey.USER);
                        dialogInterface.dismiss();
                        openActivity(LoginActivity.class);
                        animFinsh();
                    }
                });
                ibuilder1.setNegativeButton("取消", null);
                CustomDialog dialog1 = ibuilder1.create();
                dialog1.setCancelable(false);
                dialog1.show();
                break;
            case R.id.ay_set_dialog_ll:
                mDialogLl.setVisibility(View.GONE);
                break;
        }
    }


}
