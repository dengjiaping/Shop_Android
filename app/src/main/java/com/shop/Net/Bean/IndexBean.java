package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/9/28.
 */
public class IndexBean extends BaseBean {
    /**
     * ban : [{"name":"222","image":"http://img30.360buyimg.com/da/jfs/t3136/142/3009846507/196873/d7227be8/57e9df21N926e0d67.jpg","url":"http://www.google.com"},{"name":"666","image":"http://img30.360buyimg.com/da/jfs/t3226/337/3000528075/161328/ebf8ff71/57ea1bd7Nebde48b3.jpg","url":"http://www.google.com"},{"name":"111","image":"http://img30.360buyimg.com/da/jfs/t3217/193/2948281182/87481/fff0037b/57e8e5d2Na18a47b8.jpg","url":"http://www.baidu.com"},{"name":"333","image":"http://img13.360buyimg.com/da/jfs/t3124/155/2850730733/99788/c0c4cfd1/57e8eb47N1c413c46.jpg","url":"http://www.baidu.com"},{"name":"444","image":"http://img10.360buyimg.com/da/jfs/t3046/4/3011712100/98429/ec0715db/57ea2ff9Nbdc98a29.jpg","url":"http://www.baidu.com"}]
     * mid : [{"position":"2","name":"333","image":"http://img13.360buyimg.com/vclist/jfs/t3298/338/2975467237/35092/6a909557/57e9ccdbN2f799fb4.jpg","url":"www.baidu.com"},{"position":"5","name":"666","image":"http://img30.360buyimg.com/da/jfs/t3079/89/2942237711/13205/c7fbca97/57e8e373N43ecb546.jpg","url":"www.google.com"},{"position":"3","name":"444","image":"http://img10.360buyimg.com/da/jfs/t3121/318/2934194062/18434/2dbf6203/57e9c9ffN63c69b83.jpg","url":"www.baidu.com"},{"position":"4","name":"555","image":"http://img13.360buyimg.com/vclist/jfs/t3025/202/2678916445/10666/5b754788/57e4d3afN29d5a694.jpg","url":"www.baidu.com"}]
     * activity : {"name":"限量大促销","type":"1","start_time":"1475078400","end_time":"1475164800","goods":[{"type":"1","id":"9","activity_price":"8.00","price":"5.00","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg"},{"type":"1","id":"10","activity_price":"8.00","price":"2.00","title":"苹果1","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg"},{"type":"1","id":"11","activity_price":"8.00","price":"2.00","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg"},{"type":"1","id":"12","activity_price":"8.00","price":"5.00","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg"},{"type":"1","id":"13","activity_price":"8.00","price":"2.00","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg"},{"type":"1","id":"14","activity_price":"8.00","price":"2.00","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg"},{"type":"1","id":"15","activity_price":"8.00","price":"5.00","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg"},{"type":"1","id":"16","activity_price":"8.00","price":"2.00","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg"}]}
     * cgoods : [{"id":"9","name":"速冻食品","sort":"9","image":"http://img.zgtuku.com/images/front/h/6c/a2/2327127609.jpg","is_del":"0","created_time":"1474857724","list":[{"id":"110","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00"},{"id":"48","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"49","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00"}]},{"id":"8","name":"面包糕点","sort":"8","image":"http://img.zgtuku.com/images/front/h/74/3f/220053648.jpg","is_del":"0","created_time":"1474857724","list":[{"id":"105","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"44","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00"},{"id":"45","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"}]},{"id":"7","name":"进口食品","sort":"7","image":"http://img.zgtuku.com/images/front/h/68/52/2324928346.jpg","is_del":"0","created_time":"1474857724","list":[{"id":"102","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"41","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00"},{"id":"42","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"}]},{"id":"6","name":"粮油副食","sort":"6","image":"http://img.zgtuku.com/images/front/h/79/8c/2305330280.jpg","is_del":"0","created_time":"1474857724","list":[{"id":"99","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"38","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00"},{"id":"39","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"}]},{"id":"5","name":"方便食品","sort":"5","image":"http://imgws1.fruitday.com/images/2016-09-23/ed6ce03521f7b4eaa481cf455afccaf0.jpg","is_del":"0","created_time":"1474857724","list":[{"id":"123","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"33","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"34","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00"}]},{"id":"4","name":"厨房调料","sort":"4","image":"http://imgws1.fruitday.com/images/2016-09-27/774223de1eba2e0ca2527fa1bef54587.jpg","is_del":"0","created_time":"1474857724","list":[{"id":"122","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00"},{"id":"15","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"17","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00"}]},{"id":"3","name":"酒水","sort":"3","image":"http://imgws1.fruitday.com/images/2016-09-05/460368eaabc6f2009da507f8eebfeb5c.jpg","is_del":"0","created_time":"1474857724","list":[{"id":"118","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00"},{"id":"12","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"13","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00"}]},{"id":"2","name":"零食","sort":"2","image":"http://imgws1.fruitday.com/images/2016-09-27/c7324422f558a11553d83f16e4c63ab3.jpg","is_del":"0","created_time":"1474857724","list":[{"id":"114","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"6","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"7","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00"}]},{"id":"1","name":"水果","sort":"1","image":"http://imgws1.fruitday.com/images/2016-09-23/02a0a048db2c66c3f1059fb925ece188.jpg","is_del":"0","created_time":"1474857724","list":[{"id":"121","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00"},{"id":"2","title":"优选东海带鱼段","image":"http://imgqn6.fruitday.com/images/product_pic/4895/1/1-270x270-4895-7SH5WSW1.jpg","price":"2.00"},{"id":"4","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00"}]}]
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
         * name : 限量大促销
         * type : 1
         * start_time : 1475078400
         * end_time : 1475164800
         * goods : [{"type":"1","id":"9","activity_price":"8.00","price":"5.00","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg"},{"type":"1","id":"10","activity_price":"8.00","price":"2.00","title":"苹果1","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg"},{"type":"1","id":"11","activity_price":"8.00","price":"2.00","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg"},{"type":"1","id":"12","activity_price":"8.00","price":"5.00","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg"},{"type":"1","id":"13","activity_price":"8.00","price":"2.00","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg"},{"type":"1","id":"14","activity_price":"8.00","price":"2.00","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg"},{"type":"1","id":"15","activity_price":"8.00","price":"5.00","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg"},{"type":"1","id":"16","activity_price":"8.00","price":"2.00","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg"}]
         */

        private ActivityBean activity;
        /**
         * name : 222
         * image : http://img30.360buyimg.com/da/jfs/t3136/142/3009846507/196873/d7227be8/57e9df21N926e0d67.jpg
         * url : http://www.google.com
         */

        private List<BanBean> ban;
        /**
         * position : 2
         * name : 333
         * image : http://img13.360buyimg.com/vclist/jfs/t3298/338/2975467237/35092/6a909557/57e9ccdbN2f799fb4.jpg
         * url : www.baidu.com
         */

        private List<MidBean> mid;
        /**
         * id : 9
         * name : 速冻食品
         * sort : 9
         * image : http://img.zgtuku.com/images/front/h/6c/a2/2327127609.jpg
         * is_del : 0
         * created_time : 1474857724
         * list : [{"id":"110","title":"梨子","image":"http://img.zgtuku.com/images/front/h/06/fd/2178227555.jpg","price":"2.00"},{"id":"48","title":"薯片","image":"http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg","price":"5.00"},{"id":"49","title":"苹果","image":"http://img.zgtuku.com/images/front/h/e9/4b/2212228860.jpg","price":"2.00"}]
         */

        private List<CgoodsBean> cgoods;

        public ActivityBean getActivity() {
            return activity;
        }

        public void setActivity(ActivityBean activity) {
            this.activity = activity;
        }

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

        public List<CgoodsBean> getCgoods() {
            return cgoods;
        }

        public void setCgoods(List<CgoodsBean> cgoods) {
            this.cgoods = cgoods;
        }

        public static class ActivityBean {
            private String name;
            private String type;
            private String start_time;
            private String end_time;
            /**
             * type : 1
             * id : 9
             * activity_price : 8.00
             * price : 5.00
             * title : 薯片
             * image : http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg
             */

            private List<GoodsBean> goods;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                private String type;
                private String id;
                private String activity_price;
                private String price;
                private String title;
                private String image;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
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
            }
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

        public static class CgoodsBean {
            private String id;
            private String name;
            private String sort;
            private String image;
            private String is_del;
            private String created_time;
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

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getIs_del() {
                return is_del;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
            }

            public String getCreated_time() {
                return created_time;
            }

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
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
