package com.jacky.labeauty.logic.entity.module;

public class IntegralRecorder {

    /**
     * id : 5cb037e8232946a6102a01a1
     * createTime : 1555052520000
     * updateTime : 1555052520000
     * changed : 10
     * balance : 10
     * userId : 5caecf0c2329460f50e8ee52
     * type : SIGN
     * targetId : 5cb037e8232946a6102a019f
     * targetType : TASK_LOG
     * remark : 签到
     * effectiveTime : 1555052520000
     * endTime : 1555052520000
     * status : SUCCESS
     */

    private String id;
    private long createTime;
    private long updateTime;
    private int changed;
    private int balance;
    private String userId;
    private String type;
    private String targetId;
    private String targetType;
    private String remark;
    private long effectiveTime;
    private long endTime;
    private String status;

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

    public int getChanged() {
        return changed;
    }

    public void setChanged(int changed) {
        this.changed = changed;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
