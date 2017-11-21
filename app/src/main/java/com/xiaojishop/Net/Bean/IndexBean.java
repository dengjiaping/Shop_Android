package com.xiaojishop.Net.Bean;

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

        private ActivityBean activity;

        private ShopBean shop;

        private List<BanBean> ban;

        private List<MidBean> mid;

        private List<CgoodsBean> cgoods;

        public ActivityBean getActivity() {
            return activity;
        }

        public void setActivity(ActivityBean activity) {
            this.activity = activity;
        }

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
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
            private String end_time;
            /**
             * type : 1
             * id : 9
             * activity_price : 1.00
             * stock : 970
             * sale : 30
             * price : 5.00
             * title : 薯片tt
             * image : http://img.zgtuku.com/images/front_pic2/h/39/18/2136227498.jpg
             */

            private List<GoodsBean> goods;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
                private String stock;
                private String sale;
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

                public String getStock() {
                    return stock;
                }

                public void setStock(String stock) {
                    this.stock = stock;
                }

                public String getSale() {
                    return sale;
                }

                public void setSale(String sale) {
                    this.sale = sale;
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

        public static class ShopBean {
            private String name;
            private String begin_business_time;
            private String end_business_time;
            private int status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            private String id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBegin_business_time() {
                return begin_business_time;
            }

            public void setBegin_business_time(String begin_business_time) {
                this.begin_business_time = begin_business_time;
            }

            public String getEnd_business_time() {
                return end_business_time;
            }

            public void setEnd_business_time(String end_business_time) {
                this.end_business_time = end_business_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class BanBean {
            private String name;
            private String image;
            private String url;
            private String type;
            private String goods_id;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }
        }

        public static class MidBean {
            private String position;
            private String name;
            private String image;
            private String url;
            private String type;
            private String goods_id;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }
        }

        public static class CgoodsBean {
            private String id;
            private String name;
            private String image;

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
                /**
                 * pre_price : 100.00
                 */

                private String pre_price;


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

                public String getPre_price() {
                    return pre_price;
                }

                public void setPre_price(String pre_price) {
                    this.pre_price = pre_price;
                }
            }
        }
    }
}
