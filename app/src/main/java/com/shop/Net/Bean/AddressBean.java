package com.shop.Net.Bean;

import java.util.List;

/**
 * Created by admin on 2016/9/29.
 */
public class AddressBean extends BaseBean {

    /**
     * id : 15
     * user_id : 11
     * contact : 蜗蜗
     * phone : 15055889778
     * is_default : 1
     * sex : 1
     * city : {"id":"合肥市","name":null}
     * area : {"id":"蜀山区","name":null}
     * village : {"id":"华府骏苑","name":null}
     * build : {"id":"3栋","name":null}
     * unit : {"id":"5单元","name":null}
     * floor : {"id":"3层","name":null}
     * room : {"id":"301室","name":null}
     * is_del : 0
     * created_time : 1476080439
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
        private String user_id;
        private String contact;
        private String phone;
        private String is_default;
        private String sex;
        /**
         * id : 合肥市
         * name : null
         */

        private CityBean city;
        /**
         * id : 蜀山区
         * name : null
         */

        private AreaBean area;
        /**
         * id : 华府骏苑
         * name : null
         */

        private VillageBean village;
        /**
         * id : 3栋
         * name : null
         */

        private BuildBean build;
        /**
         * id : 5单元
         * name : null
         */

        private UnitBean unit;
        /**
         * id : 3层
         * name : null
         */

        private FloorBean floor;
        /**
         * id : 301室
         * name : null
         */

        private RoomBean room;
        private String is_del;
        private String created_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public AreaBean getArea() {
            return area;
        }

        public void setArea(AreaBean area) {
            this.area = area;
        }

        public VillageBean getVillage() {
            return village;
        }

        public void setVillage(VillageBean village) {
            this.village = village;
        }

        public BuildBean getBuild() {
            return build;
        }

        public void setBuild(BuildBean build) {
            this.build = build;
        }

        public UnitBean getUnit() {
            return unit;
        }

        public void setUnit(UnitBean unit) {
            this.unit = unit;
        }

        public FloorBean getFloor() {
            return floor;
        }

        public void setFloor(FloorBean floor) {
            this.floor = floor;
        }

        public RoomBean getRoom() {
            return room;
        }

        public void setRoom(RoomBean room) {
            this.room = room;
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

        public static class CityBean {
            private String id;
            private String name;

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
        }

        public static class AreaBean {
            private String id;
            private String name;

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
        }

        public static class VillageBean {
            private String id;
            private String name;

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
        }

        public static class BuildBean {
            private String id;
            private String name;

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
        }

        public static class UnitBean {
            private String id;
            private String name;

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
        }

        public static class FloorBean {
            private String id;
            private String name;

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
        }

        public static class RoomBean {
            private String id;
            private String name;

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
        }
    }
}
