package com.jacky.labeauty.logic.entity.module;

import java.io.Serializable;

// 中奖记录
public class PrizeLog implements Serializable {

    /**
     * id : 5cc7e595c3fb56801cbe999e
     * createTime : 1556604309126
     * updateTime : 1556604309126
     * name : 谢谢参与
     * prizeId : 5cc2854b2329468b8cfb5598
     * userId : 5caecf0c2329460f50e8ee52
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String name;
    private String prizeId;
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

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
