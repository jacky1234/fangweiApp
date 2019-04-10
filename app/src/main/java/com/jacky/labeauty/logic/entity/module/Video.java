package com.jacky.labeauty.logic.entity.module;

public class Video {

    /**
     * {
     * "id": "5bc01ecab45399012cc82ffe",
     * "createTime": 1541087573000,
     * "name": "浪人琵琶", // 设计视频名称
     * "intro": "保留字段", // 设计视频介绍
     * "poster": "https://static.beedee.yituizhineng.top/201811/5bdb256c98acbe676f61c057.jpg", // 设计视频封面
     * "duration": 13000, // 设计视频时长。单位毫秒
     * "size": 3714945, // 设计视频大小。单位字节
     * "url": "https://static.beedee.yituizhineng.top/201811/5bdb254998acbe676f61c055.mp4", // 设计视频地址
     * }
     */

    private String id;
    private long createTime;
    private String name;
    private String intro;
    private String poster;
    private int duration;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
