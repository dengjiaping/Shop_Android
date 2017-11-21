package com.xiaojishop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/12.
 */
public class OrderBean extends BaseBean {

    /**
     * id : 1
     * shop_name :
     * expenses : 10.00
     * total_price : 27.00
     * status : 1
     * end_time : 2016-10-12 10:07:37
     * goods : [{"id":"0","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","title":"薯片","subtitle":"一袋100克","price":"5.00","number":"1","type":"0"},{"id":"0","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","title":"苹果","subtitle":"红富士苹果","price":"2.00","number":"3","type":"0"},{"id":"0","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","title":"梨子","subtitle":"正宗砀山水梨","price":"2.00","number":"3","type":"0"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String shop_name;
        private String expenses;
        private String total_price;
        private int status;
        private String end_time;
        /**
         * id : 0
         * image : http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg
         * title : 薯片
         * subtitle : 一袋100克
         * price : 5.00
         * number : 1
         * type : 0
         */

        private List<GoodsBean> goods;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getExpenses() {
            return expenses;
        }

        public void setExpenses(String expenses) {
            this.expenses = expenses;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            private String id;
            private String image;
            private String title;
            private String subtitle;
            private String price;
            private String number;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
