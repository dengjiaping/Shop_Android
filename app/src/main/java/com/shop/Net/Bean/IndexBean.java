package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/9/28.
 */
public class IndexBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 222
         * image : http://img14.360buyimg.com/cms/jfs/t3256/282/2324505968/175172/2448654d/57e09e13Nceacbce1.jpg
         * url : www.google.com
         */

        private List<BanBean> ban;
        /**
         * position : 2
         * name : 333
         * image : http://img11.360buyimg.com/cms/jfs/t3085/88/2377100707/165269/ad578270/57e09f82Nbc26248a.jpg
         * url : www.baidu.com
         */

        private List<MidBean> mid;
        /**
         * id : 1
         * title : 苹果1
         * image : http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg
         * activity_price : 8.00
         * price : 10.00
         */

        private List<AgoodBean> agood;
        /**
         * id : 9
         * name : 速冻食品
         * image : http://img.zgtuku.com/images/front/h/6c/a2/2327127609.jpg
         * list : [{"id":"110","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00"},{"id":"48","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"49","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00"}]
         */

        private List<CgoodsBean> cgoods;

        public List<BanBean> getBan() {
            return ban;
        }

        public void setBan(List<BanBean> ban) {
            this.ban = ban;
        }

        public List<MidBean> getMid() {
            return mid;
        }

        public void setMid(List<MidBean> mid) {
            this.mid = mid;
        }

        public List<AgoodBean> getAgood() {
            return agood;
        }

        public void setAgood(List<AgoodBean> agood) {
            this.agood = agood;
        }

        public List<CgoodsBean> getCgoods() {
            return cgoods;
        }

        public void setCgoods(List<CgoodsBean> cgoods) {
            this.cgoods = cgoods;
        }

        public static class BanBean {
            private String name;
            private String image;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class MidBean {
            private String position;
            private String name;
            private String image;
            private String url;

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class AgoodBean {
            private String id;
            private String title;
            private String image;
            private String activity_price;
            private String price;

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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getActivity_price() {
                return activity_price;
            }

            public void setActivity_price(String activity_price) {
                this.activity_price = activity_price;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }

        public static class CgoodsBean {
            private String id;
            private String name;
            private String image;
            /**
             * id : 110
             * title : 梨子
             * image : http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg
             * price : 2.00
             */

            private List<ListBean> list;

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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private String id;
                private String title;
                private String image;
                private String price;

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
            }
        }
    }
}
