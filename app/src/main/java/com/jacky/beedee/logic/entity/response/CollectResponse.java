package com.jacky.beedee.logic.entity.response;

public class CollectResponse {

    /**
     * id : 5bdb15204adc2b23847fa95a
     * createTime : 1541084448617
     * targetId : 5bd707804adc2b34b062fc8f
     * targetType : GOODS
     * userId : 5bd830ef4adc2b451cedda22
     */

    private String id;
    private long createTime;
    private String targetId;
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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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
}
