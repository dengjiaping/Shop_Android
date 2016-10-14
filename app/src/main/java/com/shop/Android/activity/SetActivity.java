package com.shop.Android.activity;

import android.content.DialogInterface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Dialog.CustomDialog;
import com.king.Utils.DialogUtil;
import com.king.Utils.SPrefUtil;
import com.shop.Android.DataKey;
import com.shop.Android.base.BaseActvity;
import com.shop.R;

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
        setOnClicks(mCacheRl, mVersionRl, mAboutRl, mServiceRl,mExitRl);
    }

    @Override
    protected void onClickSet(int i) {
       switch (i){
           case R.id.ay_set_cache_rl:

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
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();
                   }
               });
               ibuilder.setNegativeButton("取消",null);
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
                       animFinsh();
                       openActivity(LoginActivity.class);
                   }
               });
               ibuilder1.setNegativeButton("取消",null);
               CustomDialog dialog1 = ibuilder1.create();
               dialog1.setCancelable(false);
               dialog1.show();
               break;
       }
    }
}
