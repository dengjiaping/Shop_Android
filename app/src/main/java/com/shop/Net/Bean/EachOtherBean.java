package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/11.
 */
public class EachOtherBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String comment_num;
        private String id;
        private String title;
        private String content;
        private String created_time;
        private String shop_id;

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }
    }
}
