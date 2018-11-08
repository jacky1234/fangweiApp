package com.jacky.beedee.logic.entity.module;

/**
 * 2018/11/5.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class Banner {

    public static transient Banner empty = new Banner();

    /**
     * id : 5ba3d5ec94603a20e4b45815
     * createTime : 1541085053000
     * updateTime : 1541085113000
     * title : 2018春款男女中长款开叉套头卫衣国潮原创中性连帽外套
     * image : http://p0.ifengimg.com/pmop/2018/1029/F7BD9C8EE2D3DFC02DE268B26ADEC1ECC2DB4B6C_size248_w564_h846.jpeg
     * link : beedee://page/goods/5bd707804adc2b34b062fc8f
     * position : default
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String title;
    private String image;
    private String link;
    private String position;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
