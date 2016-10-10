package com.shop.Net.Param;

/**
 * Created by admin on 2016/10/10.
 */
public class EditAddressParam  {
    private String token;
    private String address_id;
    private String contact;
    private String phone;
    private String is_default;
    private String sex;
    private String city;
    private String area;
    private String village;
    private String build;
    private String unit;
    private String floor;
    private String room;

    public EditAddressParam(String token, String address_id, String contact, String phone, String is_default, String sex, String city, String area, String village, String build, String unit, String floor, String room) {
        this.token = token;
        this.address_id = address_id;
        this.contact = contact;
        this.phone = phone;
        this.is_default = is_default;
        this.sex = sex;
        this.city = city;
        this.area = area;
        this.village = village;
        this.build = build;
        this.unit = unit;
        this.floor = floor;
        this.room = room;
    }
}
