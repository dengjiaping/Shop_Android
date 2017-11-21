package com.xiaojishop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/9/29.
 */
public class ClassBean extends BaseBean {


    /**
     * name : 水果
     * list : [{"id":"1","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://imgws3.fruitday.com/images/product_pic/5843/1/1-270x270-5843-R7WBC1RP.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"2","cate_id":"1","name":"水果","title":"优选东海带鱼段","subtitled":"正宗砀山水梨","image":"http://imgqn6.fruitday.com/images/product_pic/4895/1/1-270x270-4895-7SH5WSW1.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"4","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"5","cate_id":"1","name":"水果","title":"梨子","subtitled":"正宗砀山水梨","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"10","cate_id":"1","name":"水果","title":"苹果1","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"16","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"19","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"20","cate_id":"1","name":"水果","title":"梨子","subtitled":"正宗砀山水梨","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"25","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"31","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"46","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"55","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"61","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"64","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"65","cate_id":"1","name":"水果","title":"梨子","subtitled":"正宗砀山水梨","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"70","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"76","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"79","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"80","cate_id":"1","name":"水果","title":"梨子","subtitled":"正宗砀山水梨","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"85","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"91","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"106","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"115","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"},{"id":"121","cate_id":"1","name":"水果","title":"苹果","subtitled":"红富士苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00","stock":"10000","created_time":"1474857724"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String name;
        /**
         * id : 1
         * cate_id : 1
         * name : 水果
         * title : 苹果
         * subtitled : 红富士苹果
         * image : http://imgws3.fruitday.com/images/product_pic/5843/1/1-270x270-5843-R7WBC1RP.jpg
         * price : 2.00
         * stock : 10000
         * created_time : 1474857724
         */

        private List<ListBean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String id;
            private String cate_id;
            private String name;
            private String title;
            private String subtitled;
            private String image;
            private String price;
            private String stock;
            private String created_time;

            private String unit;
            private String pre_price;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getPre_price() {
                return pre_price;
            }

            public void setPre_price(String pre_price) {
                this.pre_price = pre_price;
            }
        }
    }
}
