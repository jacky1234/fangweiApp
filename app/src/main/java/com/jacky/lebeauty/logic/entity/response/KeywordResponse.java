package com.jacky.lebeauty.logic.entity.response;

public class KeywordResponse {

    /**
     * id : 5bf403f1f0fc6f12c8139327
     * createTime : 1542718449931
     * updateTime : 1542718449931
     * value : 羽绒服
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
