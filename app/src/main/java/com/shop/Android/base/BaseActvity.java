package com.shop.Android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.king.Base.KingActivity;
import com.king.Config;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public abstract class BaseActvity extends KingActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化框架的参数
        try {
            mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
        } catch (Exception e) {
        }
    }
}
