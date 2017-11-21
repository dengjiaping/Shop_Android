package com.xiaojishop.Net.Bean;

/**
 * Created by rwq on 16/12/13.
 */
public class VersionBean extends BaseBean {

    /**
     * STORE_VERSION : V1.0.1
     * ANDROID_PATH : http://dy.163.com/wemedia/notice/list/1/20.html
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String STORE_VERSION;
        private String ANDROID_PATH;

        public String getSTORE_VERSION() {
            return STORE_VERSION;
        }

        public void setSTORE_VERSION(String STORE_VERSION) {
            this.STORE_VERSION = STORE_VERSION;
        }

        public String getANDROID_PATH() {
            return ANDROID_PATH;
        }

        public void setANDROID_PATH(String ANDROID_PATH) {
            this.ANDROID_PATH = ANDROID_PATH;
        }
    }
}
