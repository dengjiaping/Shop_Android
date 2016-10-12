package com.shop.ShopCar;

import com.king.Utils.GsonUtil;

/**
 * Created by admin on 2016/10/9.
 */
public class Goods {
    private String id;//商品Id
    private String price;//商品价格
    private String image;//商品图片
    private String count;//商品数量
    private String subTitle;//商品子标题
    private String maxNum = "10000";//商品库存
    private String title;//商品名称

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    private boolean isValid = true;

    public void Copy(Goods goods){
        this.id = goods.getId();
        this.price = goods.getPrice();
        this.image = goods.getImage();
        this.count = goods.getCount();
        this.subTitle = goods.getSubTitle();
        this.maxNum = goods.getMaxNum();
        this.title = goods.getTitle();
        this.isValid = goods.isValid();
    }

    public void Clear(){
        this.id = "";
        this.price = "";
        this.image = "";
        this.count = "";
        this.subTitle = "";
        this.maxNum = "10000";
        this.title = "";
        this.isValid = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
