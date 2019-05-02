package com.jacky.labeauty.logic.entity.module;

import java.io.Serializable;

public class Address implements Serializable {

    /**
     * id : 5caed27123294643544c6c27
     * createTime : 1554961009331
     * updateTime : 1554961009331
     * name : KOMA
     * userId : 5caecf0c2329460f50e8ee52
     * province : 浙江省
     * city : 杭州市
     * area : 江干区
     * address : 克亚时代广场
     * mobile : 13500000000
     * defaultAddress : true
     * detailedAddress : 浙江省 杭州市 江干区 克亚时代广场
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String name;
    private String userId;
    private String province;
    private String city;
    private String area;
    private String address;
    private String mobile;
    private boolean defaultAddress;
    private String detailedAddress;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public static Address updateRequest(Address that) {
        final Address address = new Address();
        address.id = that.id;
        address.province = that.province;
        address.city = that.city;
        address.area = that.area;
        address.address = that.address;
        address.name = that.name;
        address.mobile = that.mobile;
        address.defaultAddress = that.defaultAddress;
        return address;
    }
}
