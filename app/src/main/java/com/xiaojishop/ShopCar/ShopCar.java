package com.xiaojishop.ShopCar;

import com.king.Utils.GsonUtil;
import com.king.Utils.LogCat;
import com.xiaojishop.Android.base.BaseActvity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 2016/10/9.
 */
public class ShopCar {

    public static boolean isNotice = true;

    public static boolean isValidCar() {
        return isValidCar;
    }

    public static void setIsValidCar(boolean isValidCar) {
        ShopCar.isValidCar = isValidCar;
    }

    private static boolean isValidCar = true;

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


    public static HashMap<String, String> getValidMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            if (((Goods) GsonUtil.Str2Bean(map.get(keys.get(i)), Goods.class)).isValid()) {
                hashMap.put(((Goods) GsonUtil.Str2Bean(map.get(keys.get(i)), Goods.class)).getId(), map.get(keys.get(i)));
            }
        }
        return hashMap;
    }

    public static ArrayList<String> getValidKeys() {
        ArrayList<String> key = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            if (((Goods) GsonUtil.Str2Bean(map.get(keys.get(i)), Goods.class)).isValid()) {
                key.add(((Goods) GsonUtil.Str2Bean(map.get(keys.get(i)), Goods.class)).getId());
            }
        }
        return key;
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

    public static void merge(Goods g, boolean isAdd) {
        if (map.containsKey(g.getId())) {
            if (isAdd) {
                map.remove(g.getId());
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

    public static int isInValid() {
        int num1 = 0;
        for (String key : keys) {
            if (((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).isValid()) {
                num1++;
            }
        }
        return num1;

    }

    public static void Invalid() {
        isValidCar = false;
        for (String key : keys) {
            Goods thing = ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class));
            thing.setValid(false);
            isNotice = false;
            merge(thing, true);
            isNotice = true;
        }
    }

    public static void Valid() {
        isValidCar = true;
        for (String key : keys) {
            Goods thing = ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class));
            thing.setValid(true);
            isNotice = false;
            merge(thing, true);
            isNotice = true;
        }
    }

    public static void notice() {
        BaseActvity.listener.sendBroadCast("CAR");
    }

    public static String allPrice() {
        double price = 0;
        for (String key : keys) {
            if (((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).isValid()) {
                price = price + Integer.valueOf(((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getCount()) * Double.valueOf(((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getPrice());
            }
        }
        return price + "";
    }

    //id:price:count#id:price:count
    public static String commit() {
        String result = "";
        for (String key : keys) {
            if (((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).isValid()) {
                if (result.isEmpty()) {
                    result = result + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getId() + ":" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getPrice() + ":" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getCount();
                } else {
                    result = result + "#" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getId() + ":" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getPrice() + ":" + ((Goods) GsonUtil.Str2Bean(map.get(key), Goods.class)).getCount();
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
