package com.shop.Net.Bean;

/**
 * Created by admin on 2016/9/29.
 */
public class UserBean extends BaseBean{

    /**
     * token : 6a56e75f35b00fc0e97b9ae3422a1743
     * is_new : true
     * user_info : {"id":"10","phone":"13856641355","nick_name":"13856641355","poster":"","gender":0,"integral":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String token;
        private String is_new;
        /**
         * id : 10
         * phone : 13856641355
         * nick_name : 13856641355
         * poster :
         * gender : 0
         * integral : 0
         */

        private UserInfoBean user_info;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public static class UserInfoBean {
            private String id;
            private String phone;
            private String nick_name;
            private String poster;
            private int gender;
            private int integral;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }
        }
    }
}
