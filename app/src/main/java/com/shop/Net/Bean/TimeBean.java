package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/12.
 */
public class TimeBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String end_time;

        private List<GoodslistBean> goodslist;

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public List<GoodslistBean> getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(List<GoodslistBean> goodslist) {
            this.goodslist = goodslist;
        }

        public static class GoodslistBean {
            private String type;
            private String id;
            private String activity_price;
            private String stock;
            private String sale;
            private String price;
            private String title;
            private String image;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getActivity_price() {
                return activity_price;
            }

            public void setActivity_price(String activity_price) {
                this.activity_price = activity_price;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getSale() {
                return sale;
            }

            public void setSale(String sale) {
                this.sale = sale;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
