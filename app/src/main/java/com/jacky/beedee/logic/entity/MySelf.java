package com.jacky.beedee.logic.entity;

import com.jacky.beedee.logic.DaoFacade;
import com.jacky.beedee.support.util.Strings;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class MySelf {
    private static MySelf instance = new MySelf();

    /**
     * id : 5bcae7fd98acbe351ecd7db3
     * createTime : 1540024317000
     * updateTime : 1540025134270
     * username : 5bcae7fd98acbe351ecd7db2
     * nickName : Koma
     * gender : MALE
     * avatar : https://static.beedee.yituizhineng.top/201810/5bcae84c98acbe351ecd7db4.jpg
     * mobile : 13500000000
     * email : koma@163.com
     * role : USER
     */

    private String id;
    private Long createTime;
    private Long updateTime;
    private String username;
    private String nickName;
    private String gender;
    private String avatar;
    private String mobile;
    private String email;
    private String role;
    private String authorization;

    public void from(@NotNull User user) {
        id = user.getId();
        createTime = user.getCreateTime();
        updateTime = user.getUpdateTime();
        username = user.getUsername();
        nickName = user.getNickName();
        gender = user.getNickName();
        avatar = user.getAvatar();
        mobile = user.getMobile();
        email = user.getEmail();
        role = user.getRole();
    }

    public static void init() {
        MySelf mySelfInfo = DaoFacade.get().getMySelfInfo();
        if (mySelfInfo != null) {
            MySelf.get().setId(mySelfInfo.getId());
            MySelf.get().setCreateTime(mySelfInfo.getCreateTime());
            MySelf.get().setUpdateTime(mySelfInfo.getUpdateTime());
            MySelf.get().setUsername(mySelfInfo.getUsername());
            MySelf.get().setNickName(mySelfInfo.getNickName());
            MySelf.get().setGender(mySelfInfo.getGender());
            MySelf.get().setAvatar(mySelfInfo.getAvatar());
            MySelf.get().setMobile(mySelfInfo.getMobile());
            MySelf.get().setEmail(mySelfInfo.getEmail());
            MySelf.get().setRole(mySelfInfo.getRole());
            MySelf.get().setAuthorization(mySelfInfo.getAuthorization());
        }
    }

    private MySelf() {
    }

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
        if (Strings.isNotBlank(nickName)) {
            return username;
        }

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


    public void save() {
        DaoFacade.get().saveMyselfInfo(instance);
    }

    public boolean isLogined() {
        return Strings.isNotBlank(id);
    }

    public void clear() {
        DaoFacade.get().saveMyselfInfo(null);
        instance = new MySelf();
    }

    @Nullable
    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(@Nullable String authorization) {
        this.authorization = authorization;
    }

    public static MySelf get() {
        return instance;
    }
}
