package com.xiaojishop.Net.Bean;

/**
 * Created by admin on 2016/10/11.
 */
public class EditUserInfoBean extends BaseBean {

    /**
     * id : 15
     * nick_name : 夏夏
     * phone : 15055889770
     * poster : http://eshop.ittapp.com/uploads/20161011/1299a3c60ce93aa5931083d0b8e6725d9843.jpg
     * gender : 0
     * integral : 0
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
        private String nick_name;
        private String phone;
        private String poster;
        private String gender;
        private String integral;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }
    }
}
