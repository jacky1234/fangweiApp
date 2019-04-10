package com.jacky.labeauty.logic.entity.response;

/**
 * 2018/11/4.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class UploadFileResponse {

    /**
     * id : 5bcae84c98acbe351ecd7db5
     * createTime : 1540024396389
     * updateTime : 1540024396389
     * key : 201810/5bcae84c98acbe351ecd7db4.jpg
     * name : LiuHuan.jpg
     * type : image/jpeg
     * size : 173306
     * url : https://static.beedee.yituizhineng.top/201810/5bcae84c98acbe351ecd7db4.jpg
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String key;
    private String name;
    private String type;
    private int size;
    private String url;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
