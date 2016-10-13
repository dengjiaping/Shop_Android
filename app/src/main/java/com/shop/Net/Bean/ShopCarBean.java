package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/11.
 */
public class ShopCarBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String stock;
        private String price;
        private String title;
        private String subtitled;
        private String image;
        private int err;
        private int newprice;
        private int newcount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitled() {
            return subtitled;
        }

        public void setSubtitled(String subtitled) {
            this.subtitled = subtitled;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getErr() {
            return err;
        }

        public void setErr(int err) {
            this.err = err;
        }

        public int getNewprice() {
            return newprice;
        }

        public void setNewprice(int newprice) {
            this.newprice = newprice;
        }

        public int getNewcount() {
            return newcount;
        }

        public void setNewcount(int newcount) {
            this.newcount = newcount;
        }
    }
}
