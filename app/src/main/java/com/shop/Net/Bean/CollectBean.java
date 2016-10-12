package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/11.
 */
public class CollectBean extends BaseBean {

    /**
     * id : 7
     * goods_id : 2
     * title : 优选东海带鱼段
     * subtitled : 正宗砀山水梨
     * image : http://imgqn6.fruitday.com/images/product_pic/4895/1/1-270x270-4895-7SH5WSW1.jpg
     * created_time : 1476081530
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
        private String goods_id;
        private String title;
        private String subtitled;
        private String image;
        private String created_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitled() {
            return subtitled;
        }

        public void setSubtitled(String subtitled) {
            this.subtitled = subtitled;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }
    }
}
