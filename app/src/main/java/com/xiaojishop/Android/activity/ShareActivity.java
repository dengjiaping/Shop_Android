package com.xiaojishop.Android.activity;

import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.R;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

/**
 * Created by admin on 2016/9/22.
 */
public class ShareActivity extends BaseActvity {
    private String TAG = "share";
    private RelativeLayout mSubmitRl;
    private UMShareAPI mShareAPI =null ;
    private UMImage image;

    @Override
    protected int loadLayout() {
        return R.layout.activity_share;

    }

    @Override
    protected void initTitleBar() {
        initTitle("分享好友");
        mTitleLeftIv.setImageResource(R.mipmap.back);

    }

    @Override
    protected void onResume() {
        super.onResume();
        PlatformConfig.setWeixin("wx8ffc982021c3e3bf", "ab196c460e07121abda47a816dba6728");
        PlatformConfig.setQQZone("1105580327", "0eAyJ7hlB2UVJO0A");
        mShareAPI = UMShareAPI.get(ShareActivity.this);
        mShareAPI.isInstall(ShareActivity.this, SHARE_MEDIA.WEIXIN);
    }

    @Override
    protected void init() {
        F();
        setOnClicks(mSubmitRl);
    }

    @Override
    protected void onClickSet(int i) {

        switch (i){
            case R.id.ay_share_submit_rl:
                ShareData();
                break;
        }

    }

    private void ShareData() {
        new ShareAction(ShareActivity.this).setDisplayList( SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN_CIRCLE)
                .withText("方便大家的网上超市！")
                .withTitle("便利店下载")
                .withMedia(new UMImage(ShareActivity.this,R.mipmap.share))
                .setListenerList(umShareListener)
                .open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(ShareActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ShareActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }
}
