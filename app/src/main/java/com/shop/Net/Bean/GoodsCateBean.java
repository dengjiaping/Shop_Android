package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/9.
 */
public class GoodsCateBean extends BaseBean {


    /**
     * cate_detail : {"id":"1","name":"水果","detail_image":"http://imgws1.fruitday.com/images/2016-09-23/02a0a048db2c66c3f1059fb925ece188.jpg"}
     * goods_list : [{"id":"2","color":"#00ffff","cate_id":"1","title":"海尔（Haier）BCD-251WDGW 251升 无霜两门冰箱（白色）","subtitled":"正宗砀山水梨","image":"http://imgqn6.fruitday.com/images/product_pic/4895/1/1-270x270-4895-7SH5WSW1.jpg","price":"0.01","stock":"1000","created_time":"1474857724"},{"id":"4","color":"#00ffff","cate_id":"1","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"5","color":"#00ffff","cate_id":"1","title":"梨子","subtitled":"正宗砀山水梨","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"10","color":"#00ffff","cate_id":"1","title":"苹果1","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"16","color":"#00ffff","cate_id":"1","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"19","color":"#00ffff","cate_id":"1","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"20","color":"#00ffff","cate_id":"1","title":"梨子","subtitled":"正宗砀山水梨","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"25","color":"#00ffff","cate_id":"1","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"31","color":"#00ffff","cate_id":"1","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"46","color":"#00ffff","cate_id":"1","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"55","color":"#00ffff","cate_id":"1","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"1000","created_time":"1474857724"},{"id":"61","color":"#00ffff","cate_id":"1","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"1000","created_time":"1474857724"}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 水果
         * detail_image : http://imgws1.fruitday.com/images/2016-09-23/02a0a048db2c66c3f1059fb925ece188.jpg
         */

        private CateDetailBean cate_detail;
        /**
         * id : 2
         * color : #00ffff
         * cate_id : 1
         * title : 海尔（Haier）BCD-251WDGW 251升 无霜两门冰箱（白色）
         * subtitled : 正宗砀山水梨
         * image : http://imgqn6.fruitday.com/images/product_pic/4895/1/1-270x270-4895-7SH5WSW1.jpg
         * price : 0.01
         * stock : 1000
         * created_time : 1474857724
         */

        private List<GoodsListBean> goods_list;

        public CateDetailBean getCate_detail() {
            return cate_detail;
        }

        public void setCate_detail(CateDetailBean cate_detail) {
            this.cate_detail = cate_detail;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class CateDetailBean {
            private String id;
            private String name;
            private String detail_image;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDetail_image() {
                return detail_image;
            }

            public void setDetail_image(String detail_image) {
                this.detail_image = detail_image;
            }
        }

        public static class GoodsListBean {
            private String id;
            private String color;
            private String cate_id;
            private String title;
            private String subtitled;
            private String image;
            private String price;
            private String stock;
            private String created_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getCreated_time() {
                return created_time;
            }

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
            }
        }
    }
}
