package com.shop.Android;

import com.king.KingApplication;
import com.king.KingConfig;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class BaseApplication extends KingApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        KingConfig.mDefaultImage = R.drawable.default_image;
        KingConfig.mCircleDefaultImage = R.drawable.default_image;
    }
}
