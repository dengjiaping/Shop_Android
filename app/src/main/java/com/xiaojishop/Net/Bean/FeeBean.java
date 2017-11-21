package com.xiaojishop.Net.Bean;

/**
 * Created by admin on 2016/10/12.
 */
public class FeeBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String freight;
        private String lowest;
        private String send_str;

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getLowest() {
            return lowest;
        }

        public void setLowest(String lowest) {
            this.lowest = lowest;
        }

        public String getSend_str() {
            return send_str;
        }

        public void setSend_str(String send_str) {
            this.send_str = send_str;
        }
    }
}
