package com.jacky.labeauty.logic.entity.module;

public class Message {

    /**
     * id : 5cc7c518c3fb56a6c4bbea8e
     * createTime : 1556595993000
     * userId : 5caecf0c2329460f50e8ee52
     * title : 积分变更通知
     * content : 你的积分已增长至252710积分，可参与抽奖，奖品丰富，欢迎参与。
     * type : BONUS_POINT
     */

    private String id;
    private long createTime;
    private String userId;
    private String title;
    private String content;
    private String type;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
