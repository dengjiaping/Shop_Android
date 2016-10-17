package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/14.
 */
public class EvaluateBean extends BaseBean {

    /**
     * id : 302
     * goods_image : http://imgws3.fruitday.com/images/product_pic/5843/1/1-270x270-5843-R7WBC1RP.jpg
     * goods_title : haier海尔 BC-93TMPF 93升单门冰箱
     * goods_subtitle : 红富士苹果
     * goods_price : 1.00
     * type : 3
     * content : 难吃
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
        private String goods_image;
        private String goods_title;
        private String goods_subtitle;
        private String goods_price;
        private String type;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getGoods_title() {
            return goods_title;
        }

        public void setGoods_title(String goods_title) {
            this.goods_title = goods_title;
        }

        public String getGoods_subtitle() {
            return goods_subtitle;
        }

        public void setGoods_subtitle(String goods_subtitle) {
            this.goods_subtitle = goods_subtitle;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
