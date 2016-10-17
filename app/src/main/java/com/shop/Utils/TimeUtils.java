package com.shop.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by apple on 16/7/12.
 */
public class TimeUtils {
    /**
     * 获取当前时间戳 (单位秒)
     */
    public static long now() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 日期转时间戳 日期,格式"yyyy-MM-dd HH:mm" 转换失败返回-1
     */
    public static long date2unix2(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime()/1000;
        } catch (ParseException e) {
            return -1;
        }
    }
}
