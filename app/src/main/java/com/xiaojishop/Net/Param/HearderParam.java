package com.xiaojishop.Net.Param;

import java.io.File;

/**
 * Created by admin on 2016/10/8.
 */
public class HearderParam extends Token {
    private String gender;
    private String name;
    private File poster;

    public HearderParam( String gender, String name, File poster) {
        this.gender = gender;
        this.name = name;
        this.poster = poster;
    }
}
