package com.xiaojishop.Net.Param;

import com.king.Utils.GsonUtil;
import com.king.Utils.SPrefUtil;
import com.xiaojishop.Android.SPKey;
import com.xiaojishop.Net.Bean.IndexBean;

/**
 * Created by admin on 2016/10/12.
 */
public class Order extends Token {
    private String type;
    private String goodsinfo;
    private String address;
    private String totalprice;
    private String remark;
    private String name;
    private String phone;

    public Order(String type, String goodsinfo, String address, String totalprice, String remark, String name, String phone) {
        this.type = type;
        this.goodsinfo = goodsinfo;
        this.address = address;
        this.totalprice = totalprice;
        this.remark = remark;
        this.name = name;
        this.phone = phone;
    }

    private String shopid = ((IndexBean)GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX,""),IndexBean.class)).getData().getShop().getId();
    private String shopname = ((IndexBean)GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX,""),IndexBean.class)).getData().getShop().getName();
}
