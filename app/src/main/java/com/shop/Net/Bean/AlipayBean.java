package com.shop.Net.Bean;

/**
 * Created by admin on 2016/10/12.
 */
public class AlipayBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String java;

        private IosBean ios;

        public String getJava() {
            return java;
        }

        public void setJava(String java) {
            this.java = java;
        }

        public IosBean getIos() {
            return ios;
        }

        public void setIos(IosBean ios) {
            this.ios = ios;
        }

        public static class IosBean {
            private String _input_charset;
            private String body;
            private String int_b_pay;
            private String notify_url;
            private String out_trade_no;
            private String partner;
            private int payment_type;
            private String seller_id;
            private String service;
            private String subject;
            private String total_fee;
            private String sign;
            private String sign_type;

            public String get_input_charset() {
                return _input_charset;
            }

            public void set_input_charset(String _input_charset) {
                this._input_charset = _input_charset;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getInt_b_pay() {
                return int_b_pay;
            }

            public void setInt_b_pay(String int_b_pay) {
                this.int_b_pay = int_b_pay;
            }

            public String getNotify_url() {
                return notify_url;
            }

            public void setNotify_url(String notify_url) {
                this.notify_url = notify_url;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getPartner() {
                return partner;
            }

            public void setPartner(String partner) {
                this.partner = partner;
            }

            public int getPayment_type() {
                return payment_type;
            }

            public void setPayment_type(int payment_type) {
                this.payment_type = payment_type;
            }

            public String getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(String seller_id) {
                this.seller_id = seller_id;
            }

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getSign_type() {
                return sign_type;
            }

            public void setSign_type(String sign_type) {
                this.sign_type = sign_type;
            }
        }
    }
}
