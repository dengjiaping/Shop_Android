package com.shop.ShopCar;

import com.king.Utils.GsonUtil;
import com.king.Utils.LogCat;
import com.shop.Android.base.BaseActvity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 2016/10/9.
 */
public class TMShopCar {

    public static boolean isNotice = true;

    private static HashMap<String, String> map = new HashMap<>();

    public static ArrayList<String> getKeys() {
        return keys;
    }

    private static ArrayList<String> keys = new ArrayList<>();

    private static int num = 0;

    public static int getNum() {
        return num;
    }

    public static HashMap<String, String> getMap() {
        return map;
    }

    public static void ClearSelf() {
        isNotice = true;
        map.clear();
        keys.clear();
        num = 0;
    }

    public static void mergeButNotAdd(Goods g, boolean isAdd) {
        if (map.containsKey(g.getId())) {
            if (isAdd) {
                map.remove(g.getId());
                map.put(g.getId(), GsonUtil.Bean2Str(g));
            }
        }
        if (isNotice) {
            notice();
        }
    }

    public static void add(Goods g) {
        if (map.containsKey(g.getId())) {
            num = num + Integer.valueOf(g.getCount());
            g.setCount(Integer.valueOf(g.getCount()) + Integer.valueOf(((Goods) GsonUtil.Str2Bean(map.get(g.getId()), Goods.class)).getCount()) + "");
            if (g.getCount().equals("0")) {
                map.remove(g.getId());
                keys.remove(g.getId());
            } else {
                map.put(g.getId(), GsonUtil.Bean2Str(g));
            }
        } else {
            num = num + Integer.valueOf(g.getCount());
            keys.add(g.getId());
            map.put(g.getId(), GsonUtil.Bean2Str(g));
        }
        if (isNotice) {
            notice();
        }
    }

    public static void delete(String id) {
        if (map.get(id) != null) {
            num = num - Integer.valueOf(((Goods) GsonUtil.Str2Bean(map.get(id), Goods.class)).getCount());
            map.remove(id);
            keys.remove(id);
        }
    }

    public static void print() {
        for (String key : keys) {
            LogCat.i(key, map.get(key));
        }
    }

    public static void notice() {
        BaseActvity.listener.sendBroadCast("CAR");
    }

    public static String allPrice() {
        double price = 0;
        for (String key : keys) {
            price = price + Integer.valueOf(((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getCount()) * Double.valueOf(((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getPrice());
        }
        return price + "";
    }

    //id:price:count#id:price:count
    public static String commit() {
        String result = "";
        for (String key : keys) {
            if (((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).isValid()) {
                if (result.isEmpty()) {
                    result = result + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getId() + ":" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getPrice() + ":" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getCount() + ":" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getType();
                } else {
                    result = result + "#" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getId() + ":" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getPrice() + ":" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getCount() + ":"+ ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getType();
                }
            }
        }
        return result;
    }


    //id,id,id
    public static String clear() {
        String result = "";
        for (String key : keys) {
            if (result.isEmpty()) {
                result = result + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getId();
            } else {
                result = result + "," + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getId();
            }
        }
        return result;
    }
}
