package com.jacky.labeauty.logic.entity.module;

public class MyIntegral {

    /**
     * id : 5cb037e8232946a6102a01a0
     * createTime : 1555052520000
     * updateTime : 1555052520000
     * userId : 5caecf0c2329460f50e8ee52
     * bonusPoints : 10  积分
     * disabled : false
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String userId;
    private int bonusPoints;
    private boolean disabled;

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

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
