package com.xiaojishop.Net.Param;

/**
 * Created by admin on 2016/10/12.
 */
public class Pay extends Token {
    private String order_id;
    private String paytype;

    public Pay(String order_id, String paytype) {
        this.order_id = order_id;
        this.paytype = paytype;
    }
}
