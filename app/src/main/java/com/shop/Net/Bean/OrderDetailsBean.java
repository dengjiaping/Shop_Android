package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/13.
 */
public class OrderDetailsBean extends BaseBean {

    /**
     * id : 132
     * shop_name : 小小便利店（蔚蓝商务港）
     * order_number : 1610141119258553
     * remark :
     * expenses : 10.00
     * total_price : 15.00
     * pay_type : 1
     * status : 7
     * receiver_phone : 18096674262
     * receiver_contacts : 魏亮
     * receiver_address : 合肥市蜀山区华府骏苑3栋5单元3层301室
     * created_time : 2016-10-14 11:19:25
     * end_time : 0
     * send_time :
     * goods : [{"id":"307","image":"http://imgqn6.fruitday.com/images/product_pic/4895/1/1-270x270-4895-7SH5WSW1.jpg","title":"海尔（Haier）BCD-251WDGW 251升 无霜两门冰箱（白色）","subtitle":"正宗砀山水梨","price":"1.00","number":"1","type":"1"},{"id":"308","image":"http://imgqn5.fruitday.com/images/product_pic/12207/1/1-270x270-12207-SR6PACY3.jpg","title":"南翔小笼包","subtitle":"一袋100克","price":"1.00","number":"1","type":"1"},{"id":"309","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","title":"薯片","subtitle":"一袋100克","price":"1.00","number":"3","type":"1"}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String shop_name;
        private String order_number;
        private String remark;
        private String expenses;
        private String total_price;
        private String pay_type;
        private int status;
        private String receiver_phone;
        private String receiver_contacts;
        private String receiver_address;
        private String created_time;
        private String end_time;
        private String send_time;
        /**
         * id : 307
         * image : http://imgqn6.fruitday.com/images/product_pic/4895/1/1-270x270-4895-7SH5WSW1.jpg
         * title : 海尔（Haier）BCD-251WDGW 251升 无霜两门冰箱（白色）
         * subtitle : 正宗砀山水梨
         * price : 1.00
         * number : 1
         * type : 1
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

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getReceiver_phone() {
            return receiver_phone;
        }

        public void setReceiver_phone(String receiver_phone) {
            this.receiver_phone = receiver_phone;
        }

        public String getReceiver_contacts() {
            return receiver_contacts;
        }

        public void setReceiver_contacts(String receiver_contacts) {
            this.receiver_contacts = receiver_contacts;
        }

        public String getReceiver_address() {
            return receiver_address;
        }

        public void setReceiver_address(String receiver_address) {
            this.receiver_address = receiver_address;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
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
