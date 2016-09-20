package com.shop.Android;

import com.king.Config;
import com.king.KingApplication;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class BaseApplication extends KingApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Config.mDefaultImage = R.drawable.default_image;
        Config.mCircleDefaultImage = R.drawable.default_image;
    }
}
