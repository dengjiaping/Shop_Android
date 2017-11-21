package com.xiaojishop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/14.
 */
public class IntegralBean extends BaseBean {

    /**
     * poster : /uploads/20161012/7129f0347eaf3dc8fcf9e47610ee3bfb6e50.JPG
     * integral : 58
     * grade : 青铜
     * order_integrals : [{"num":"10","order_number":"1610141050545081","created_time":"2016-10-14 10:50:54"},{"num":"12","order_number":"1610141103394444","created_time":"2016-10-14 11:03:39"},{"num":"11","order_number":"1610141115242534","created_time":"2016-10-14 11:15:24"},{"num":"15","order_number":"1610141119258553","created_time":"2016-10-14 11:19:25"},{"num":"10","order_number":"1610141127496552","created_time":"2016-10-14 11:27:49"},{"num":"16","order_number":"1610141508539633","created_time":"2016-10-14 15:08:53"},{"num":"25","order_number":"1610141711598405","created_time":"2016-10-14 17:11:59"},{"num":"25","order_number":"1610141711598405","created_time":"2016-10-14 17:11:59"},{"num":"25","order_number":"1610141711598405","created_time":"2016-10-14 17:11:59"},{"num":"25","order_number":"1610141711598405","created_time":"2016-10-14 17:11:59"},{"num":"25","order_number":"1610141711598405","created_time":"2016-10-14 17:11:59"},{"num":"10","order_number":"1610141718597194","created_time":"2016-10-14 17:18:59"},{"num":"25","order_number":"1610141711598405","created_time":"2016-10-14 17:11:59"},{"num":"10","order_number":"1610141718597194","created_time":"2016-10-14 17:18:59"},{"num":"10","order_number":"1610141718597194","created_time":"2016-10-14 17:18:59"},{"num":"10","order_number":"1610141718597194","created_time":"2016-10-14 17:18:59"},{"num":"10","order_number":"1610141718597194","created_time":"2016-10-14 17:18:59"},{"num":"10","order_number":"1610141718597194","created_time":"2016-10-14 17:18:59"}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String poster;
        private String integral;
        private String grade;
        /**
         * num : 10
         * order_number : 1610141050545081
         * created_time : 2016-10-14 10:50:54
         */

        private List<OrderIntegralsBean> order_integrals;

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public List<OrderIntegralsBean> getOrder_integrals() {
            return order_integrals;
        }

        public void setOrder_integrals(List<OrderIntegralsBean> order_integrals) {
            this.order_integrals = order_integrals;
        }

        public static class OrderIntegralsBean {
            private String num;
            private String order_number;
            private String created_time;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getOrder_number() {
                return order_number;
            }

            public void setOrder_number(String order_number) {
                this.order_number = order_number;
            }

            public String getCreated_time() {
                return created_time;
            }

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
            }
        }
    }
}
