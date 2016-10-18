package com.shop.Android.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.king.Base.KingActivity;
import com.king.Base.KingData;
import com.pgyersdk.crash.PgyCrashManager;
import com.shop.Android.Config;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public abstract class BaseActvity extends KingActivity {



    public static KingData listener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化框架的参数
        try {
            mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
            mTitleLeftIv.setImageResource(R.mipmap.back);
        } catch (Exception e) {
        }
        listener = kingData;

        PgyCrashManager.register(this);
    }

    protected void clearData() {
        Config.DATA = "";
        Config.TYPE = 0;
    }

    protected Intent openDataAct(Class cla, String data) {
        Intent intent = new Intent(mContext, cla);
        intent.putExtra("data", data);
        startActivity(intent);
        overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);
        return intent;
    }
}
