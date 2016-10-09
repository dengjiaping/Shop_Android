package com.shop.Net.Param;

import java.io.File;

/**
 * Created by admin on 2016/10/8.
 */
public class HearderParam {
    private String token ;
    private String gender;
    private String name;
    private File poster;

    public HearderParam(String token, String gender, String name, File poster) {
        this.token = token;
        this.gender = gender;
        this.name = name;
        this.poster = poster;
    }
}
