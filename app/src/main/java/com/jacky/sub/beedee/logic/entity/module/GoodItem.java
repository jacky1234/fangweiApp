package com.jacky.sub.beedee.logic.entity.module;

/**
 * 2018/11/6.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class GoodItem {

    /**
     * id : 5bd707804adc2b34b062fc8f
     * createTime : 1540894496000
     * name : BEEDEE 2018春款男女中长款开叉套头卫衣国潮原创中性连帽外套W07
     * intro : 保留字段
     * thumb : http://p0.ifengimg.com/pmop/2018/1029/F7BD9C8EE2D3DFC02DE268B26ADEC1ECC2DB4B6C_size248_w564_h846.jpeg
     * price : 130
     * collected : false
     * collectCount : 0
     */

    private String id;
    private long createTime;
    private String name;
    private String intro;
    private String thumb;
    private int price;
    private boolean collected;
    private int collectCount;
    public static GoodItem empty = new GoodItem();

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
}
