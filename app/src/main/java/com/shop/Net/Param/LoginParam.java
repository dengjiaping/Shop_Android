package com.shop.Net.Param;

/**
 * Created by admin on 2016/9/29.
 */
public class LoginParam {

    private String verify_code;
    private String phone_number;

    public LoginParam(String verify_code, String phone_number) {
        this.verify_code = verify_code;
        this.phone_number = phone_number;
    }
}
