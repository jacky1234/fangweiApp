package com.jacky.beedee.logic.entity.module;

import java.util.List;

public class Good {

    /**
     * {
     * "id": "5bd707804adc2b34b062fc8f",
     * "createTime": 1540894496000,
     * "name": "BEEDEE 2018春款男女中长款开叉套头卫衣国潮原创中性连帽外套W07",// 商品名称
     * "intro": "保留字段", // 商品介绍
     * "thumb": "http://p0.ifengimg.com/pmop/2018/1029/F7BD9C8EE2D3DFC02DE268B26ADEC1ECC2DB4B6C_size248_w564_h846.jpeg", // 商品主图
     * "gallery": [
     * "https://cbu01.alicdn.com/img/ibank/2018/555/647/8607746555_512826682.jpg",
     * "https://cbu01.alicdn.com/img/ibank/2018/555/647/8607746555_512826682.64x64.jpg",
     * "https://cbu01.alicdn.com/img/ibank/2018/054/196/8573691450_512826682.64x64.jpg"
     * ], // 商品轮播图
     * "details": [
     * "https://cbu01.alicdn.com/img/ibank/2018/654/557/8607755456_512826682.jpg",
     * "https://cbu01.alicdn.com/img/ibank/2018/784/886/8573688487_512826682.jpg",
     * "https://cbu01.alicdn.com/img/ibank/2018/337/036/8593630733_512826682.jpg",
     * "https://cbu01.alicdn.com/img/ibank/2018/780/427/8573724087_512826682.jpg"
     * ], // 商品详情图
     * "price": 130, // 商品价格
     * "collected": false // 是否已收藏
     * "collectCount": 0 // 被收藏数量
     * }
     */

    private String id;
    private long createTime;
    private String name;
    private String intro;
    private String thumb;
    private int price;
    private boolean collected;
    private int collectCount;
    private List<String> gallery;
    private List<String> details;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
