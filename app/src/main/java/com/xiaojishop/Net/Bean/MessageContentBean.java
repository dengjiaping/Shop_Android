package com.xiaojishop.Net.Bean;

/**
 * Created by admin on 2016/10/10.
 */
public class MessageContentBean extends BaseBean {

    /**
     * id : 1
     * title : 国庆大酬宾
     * content : 欢度国庆喜迎佳节
     * created_time : 1974-08-11 12:02:25
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
        private String title;
        private String content;
        private String created_time;

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
    }
}
