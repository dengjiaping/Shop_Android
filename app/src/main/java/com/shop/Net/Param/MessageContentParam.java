package com.shop.Net.Param;

/**
 * Created by admin on 2016/10/10.
 */
public class MessageContentParam {
    private String token;
    private String message_id;

    public MessageContentParam(String token, String message_id) {
        this.token = token;
        this.message_id = message_id;
    }
}
