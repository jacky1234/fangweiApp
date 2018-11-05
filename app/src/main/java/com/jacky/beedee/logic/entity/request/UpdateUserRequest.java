package com.jacky.beedee.logic.entity.request;

/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class UpdateUserRequest {

    /**
     * nickName : Koma
     * avatar : https://static.beedee.yituizhineng.top/201810/5bcae84c98acbe351ecd7db4.jpg
     * gender : MALE
     * password : 123
     * email : koma@163.com
     */

    private String nickName;
    private String avatar;
    private String gender;
    private String password;
    private String email;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
