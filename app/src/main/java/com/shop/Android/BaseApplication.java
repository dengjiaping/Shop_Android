package com.shop.Android;

import com.king.Internet.user_method.CallServer;
import com.king.KingApplication;
import com.king.KingConfig;
import com.pgyersdk.crash.PgyCrashManager;
import com.shop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class BaseApplication extends KingApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        CallServer.BASE_URL = "http://eshop.ittapp.com/api.php/%s";
        Config.WEB_URL = "http://eshop.ittapp.com/api.php/";
        KingConfig.mDefaultImage = R.drawable.default_image;
        KingConfig.mCircleDefaultImage = R.drawable.circle_default_image;

        PgyCrashManager.register(this);
    }
}
