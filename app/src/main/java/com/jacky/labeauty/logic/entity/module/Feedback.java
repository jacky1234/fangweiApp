package com.jacky.labeauty.logic.entity.module;

public class Feedback {

    /**
     * id : 5bf4009cf0fc6f10ecd38a98
     * createTime : 1542717596582
     * updateTime : 1542717596582
     * content : 登录不了
     * contact : 13500000000
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String content;
    private String contact;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
