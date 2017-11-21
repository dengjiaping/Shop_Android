package com.xiaojishop.Net.Param;

import com.king.Utils.GsonUtil;
import com.king.Utils.SPrefUtil;
import com.xiaojishop.Android.DataKey;
import com.xiaojishop.Net.Bean.UserBean;

/**
 * Created by admin on 2016/10/10.
 */
public class Token {
    private String token = SPrefUtil.Function.getData(DataKey.USER,"").equals("") ?  "": ((UserBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(DataKey.USER,""), UserBean.class)).getData().getToken();

}
