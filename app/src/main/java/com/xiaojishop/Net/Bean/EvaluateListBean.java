package com.xiaojishop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/18.
 */
public class EvaluateListBean extends BaseBean {

    /**
     * id : 132
     * created_time : 2016-10-14
     * order_number : 1610141119258553
     * shop_name : 小小便利店（蔚蓝商务港）
     * type : 2
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
        private String created_time;
        private String order_number;
        private String shop_name;
        private int type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
