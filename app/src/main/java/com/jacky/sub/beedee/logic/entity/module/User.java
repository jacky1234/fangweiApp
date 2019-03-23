package com.jacky.sub.beedee.logic.entity.module;

/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class User {

    /**
     * id : 5bcae7fd98acbe351ecd7db3
     * createTime : 1540024317000
     * updateTime : 1540025134270
     * username : 5bcae7fd98acbe351ecd7db2
     * nickName : Koma
     * gender : MALE        性别。MALE：男，FEMALE：女
     * avatar : https://static.beedee.yituizhineng.top/201810/5bcae84c98acbe351ecd7db4.jpg
     * mobile : 13500000000
     * email : koma@163.com
     * role : USER
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String username;
    private String nickName;
    private String gender;
    private String avatar;
    private String mobile;
    private String email;
    private String role;
    private boolean hasPassword;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }
}
