package com.jacky.beedee.logic.entity.request;

public class CollectRequest {

    /**
     * {
     * "targetId": "5bd707804adc2b34b062fc8f", // 收藏对象ID
     * "targetType": "GOODS" // 收藏对象类型。GOODS：商品 OUTFIT：穿搭
     * }
     */

    private String targetId;
    private String targetType;

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
}
