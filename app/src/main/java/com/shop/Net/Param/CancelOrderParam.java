package com.shop.Net.Param;

/**
 * Created by admin on 2016/10/12.
 */
public class CancelOrderParam extends Token {
    private String orderid;

    public CancelOrderParam(String orderid) {
        this.orderid = orderid;
    }
}
