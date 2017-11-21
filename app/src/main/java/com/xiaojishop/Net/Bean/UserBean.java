package com.xiaojishop.Net.Bean;

/**
 * Created by admin on 2016/9/29.
 */
public class UserBean extends BaseBean{


    /**
     * token : 4036f54847ee0a0767520fe9fa78ab6e
     * is_new : false
     * user_info : {"id":"11","nick_name":"忍晚秋","phone":"17858868606","poster":"/uploads/20161009/97972b81608b4cbcc7cb0899833f1ceceb80.jpg","gender":"0","integral":"0"}
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
         * id : 11
         * nick_name : 忍晚秋
         * phone : 17858868606
         * poster : /uploads/20161009/97972b81608b4cbcc7cb0899833f1ceceb80.jpg
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
}
