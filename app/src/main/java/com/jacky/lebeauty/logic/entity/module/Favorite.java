package com.jacky.lebeauty.logic.entity.module;

//收藏
public class Favorite {

    /**
     * id : 5bdb15204adc2b23847fa95a
     * createTime : 1541084449000
     * target : {"id":"5bd707804adc2b34b062fc8f","createTime":1540894496000,"name":"BEEDEE 2018春款男女中长款开叉套头卫衣国潮原创中性连帽外套W07","intro":"2018春款男女中长款开叉套头卫衣国潮原创中性连帽外套","thumb":"http://p0.ifengimg.com/pmop/2018/1029/F7BD9C8EE2D3DFC02DE268B26ADEC1ECC2DB4B6C_size248_w564_h846.jpeg","price":130,"collected":true,"collectCount":1}
     * targetType : GOODS
     * userId : 5bd830ef4adc2b451cedda22
     */

    private String id;
    private long createTime;
    private TargetBean target;
    private String targetType;
    private String userId;

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

    public TargetBean getTarget() {
        return target;
    }

    public void setTarget(TargetBean target) {
        this.target = target;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class TargetBean {
        /**
         * id : 5bd707804adc2b34b062fc8f
         * createTime : 1540894496000
         * name : BEEDEE 2018春款男女中长款开叉套头卫衣国潮原创中性连帽外套W07
         * intro : 2018春款男女中长款开叉套头卫衣国潮原创中性连帽外套
         * thumb : http://p0.ifengimg.com/pmop/2018/1029/F7BD9C8EE2D3DFC02DE268B26ADEC1ECC2DB4B6C_size248_w564_h846.jpeg
         * price : 130
         * collected : true
         * collectCount : 1
         */

        private String id;
        private long createTime;
        private String name;
        private String intro;
        private String thumb;
        private int price;
        private boolean collected;
        private int collectCount;

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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public boolean isCollected() {
            return collected;
        }

        public void setCollected(boolean collected) {
            this.collected = collected;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }
    }
}
