package com.shop.Net.Param;

/**
 * Created by admin on 2016/10/12.
 */
public class OrderWaitPayParam extends Token {
    private String ordertype;
    private String page;

    public OrderWaitPayParam(String ordertype, String page) {
        this.ordertype = ordertype;
        this.page = page;
    }
}
