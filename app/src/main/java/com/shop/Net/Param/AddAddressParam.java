package com.shop.Net.Param;

/**
 * Created by admin on 2016/10/8.
 */
public class AddAddressParam {
    private String contact;
    private String phone;
    private String is_default;
    private String sex;
    private String city;
    private String area;
    private String village;
    private String unit;
    private String floor;
    private String room;
    private String token;

    public AddAddressParam(String contact, String phone, String is_default, String sex, String city, String area, String village, String unit, String floor, String room, String token) {
        this.contact = contact;
        this.phone = phone;
        this.is_default = is_default;
        this.sex = sex;
        this.city = city;
        this.area = area;
        this.village = village;
        this.unit = unit;
        this.floor = floor;
        this.room = room;
        this.token = token;
    }
}
