package com.jacky.beedee.logic.entity.response;

public class RegisterResponse {

    /**
     * id : 5bcae7fd98acbe351ecd7db3
     * createTime : 1540024317052
     * updateTime : 1540024317052
     * username : 5bcae7fd98acbe351ecd7db2
     * mobile : 13500000000
     * role : USER
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String username;
    private String mobile;
    private String role;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
