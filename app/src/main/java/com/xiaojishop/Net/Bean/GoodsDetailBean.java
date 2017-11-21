package com.xiaojishop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/10/8.
 */
public class GoodsDetailBean extends BaseBean{


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String id;
        private String subtitled;
        private String title;
        private String image;
        private String price;
        private String intro;




        /**
         * url : http://eshop.ittapp.com/api.php/question/intro?id=110
         */

        private String url;
        /**
         * remark :
         */

        private String remark;
        /**
         * unit :
         */

        private String unit;
        /**
         * pre_price : 100.00
         */

        private String pre_price;


        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        private String activity_id;
        private String comment_num;
        private List<String> detail_image;
        private List<RecommendBean> recommend;
        private String activity_price;
        private String is_collect;

        public DataBean() {
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String type;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubtitled() {
            return subtitled;
        }

        public void setSubtitled(String subtitled) {
            this.subtitled = subtitled;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public List<String> getDetail_image() {
            return detail_image;
        }

        public void setDetail_image(List<String> detail_image) {
            this.detail_image = detail_image;
        }

        public List<RecommendBean> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<RecommendBean> recommend) {
            this.recommend = recommend;
        }

        public String getActivity_price() {
            return activity_price;
        }

        public void setActivity_price(String activity_price) {
            this.activity_price = activity_price;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public static class RecommendBean {
            private int id;

            private String title;
            private String subtitled;
            private String image;
            private String price;
            /**
             * activity_id : 8
             * unit :
             * pre_price : 100.00
             */

            private String activity_id;
            private String unit;
            private String pre_price;


            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            private String type;
            /**
             * activity_price : 8.00
             */

            private String activity_price;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getActivity_price() {
                return activity_price;
            }

            public void setActivity_price(String activity_price) {
                this.activity_price = activity_price;
            }

            public String getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
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
