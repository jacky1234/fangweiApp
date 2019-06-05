package com.jacky.labeauty.logic.entity.module;

import java.io.Serializable;
import java.util.List;

public class DefakeBean implements Serializable {

    /**
     * id : 5cadac4fafcae8566411b028
     * createTime : 1554885711000
     * updateTime : 1554975514000
     * name : ANCILA
     * thumb : https://static.labeauty.yituizhineng.top/201904/5caf0ac223294630087da9c2_s50806_w850_h850.jpg
     * attrs : [{"key":"产地","value":"杭州"}]
     * agency : {"id":"5caef7e723294661944074a8","createTime":1554970600000,"updateTime":1554975437000,"userId":"5caecf0c2329460f50e8ee52","logo":"https://static.labeauty.yituizhineng.top/201904/5caf0ac223294630087da9c2_s50806_w850_h850.jpg","sn":"1","name":"KK","intro":"哈哈哈","address":"杭州"}
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String name;
    private String thumb;
    private AgencyBean agency;
    private List<AttrsBean> attrs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public AgencyBean getAgency() {
        return agency;
    }

    public void setAgency(AgencyBean agency) {
        this.agency = agency;
    }

    public List<AttrsBean> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<AttrsBean> attrs) {
        this.attrs = attrs;
    }

    public static class AgencyBean implements Serializable {
        /**
         * id : 5caef7e723294661944074a8
         * createTime : 1554970600000
         * updateTime : 1554975437000
         * userId : 5caecf0c2329460f50e8ee52
         * logo : https://static.labeauty.yituizhineng.top/201904/5caf0ac223294630087da9c2_s50806_w850_h850.jpg
         * sn : 1
         * name : KK
         * intro : 哈哈哈
         * address : 杭州
         */

        private String id;
        private long createTime;
        private long updateTime;
        private String userId;
        private String logo;
        private String sn;
        private String name;
        private String intro;
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class AttrsBean implements Serializable {
        /**
         * key : 产地
         * value : 杭州
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
