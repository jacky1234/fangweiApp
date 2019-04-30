package com.jacky.labeauty.logic.entity.request;

public class AddAddressRequest {

    /**
     * province : 浙江省
     * city : 杭州市
     * area : 江干区
     * address : 克亚时代广场
     * name : KOMA
     * mobile : 13500000000
     * defaultAddress : true
     */

    private String province;
    private String city;
    private String area;
    private String address;
    private String name;
    private String mobile;
    private boolean defaultAddress;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
