package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/12.
 */
public class OrderInfoBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id :
         * number :
         * end_time :
         */

        private OrderinfoBean orderinfo;
        /**
         * id : 2
         * stock : 1000
         * price : 1.00
         * image : http://imgqn6.fruitday.com/images/product_pic/4895/1/1-270x270-4895-7SH5WSW1.jpg
         * title : 海尔（Haier）BCD-251WDGW 251升 无霜两门冰箱（白色）
         * subtitled : 正宗砀山水梨
         * err : 5
         * newprice : 0
         * newcount : 0
         */

        private List<FailBean> fail;

        public OrderinfoBean getOrderinfo() {
            return orderinfo;
        }

        public void setOrderinfo(OrderinfoBean orderinfo) {
            this.orderinfo = orderinfo;
        }

        public List<FailBean> getFail() {
            return fail;
        }

        public void setFail(List<FailBean> fail) {
            this.fail = fail;
        }

        public static class OrderinfoBean {
            private String id;
            private String number;
            private String end_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }
        }

        public static class FailBean {
            private String id;
            private String stock;
            private String price;
            private String image;
            private String title;
            private String subtitled;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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
}
