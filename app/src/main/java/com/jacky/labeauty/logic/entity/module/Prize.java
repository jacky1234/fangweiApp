package com.jacky.labeauty.logic.entity.module;

import java.io.Serializable;

public class Prize implements Serializable {

    /**
     * "id": "5cc2854b2329468b8cfb5598",
     * "createTime": 1556251980000,
     * "updateTime": 1556259148000,
     * "name": "谢谢参与", // 奖品名称
     * "thumb": "", // 奖品图片
     * "targetId": "5caed8472329462c9037a7c3",
     * "targetType": "EMPTY" // 奖品类型。EMPTY：未中奖，GIFT：实物，COUPON：优惠券， BONUS_POINT：积分
     */

    public static final String TARGET_TYPE_EMPTY = "EMPTY";
    public static final String TARGET_TYPE_GIFT = "GIFT";
    public static final String TARGET_TYPE_COUPON = "COUPON";
    public static final String TARGET_TYPE_INTEGRAL = "BONUS_POINT";

    private String id;
    private long createTime;
    private long updateTime;
    private String name;
    private String thumb;
    private String targetId;
    private String targetType;

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
