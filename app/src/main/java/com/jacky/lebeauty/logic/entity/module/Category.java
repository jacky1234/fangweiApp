package com.jacky.lebeauty.logic.entity.module;

/**
 * 2018/11/9.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class Category {

    /**
     * id : 5bdbbfed4adc2b01104db527
     * createTime : 1541128173000
     * name : 2018秋季第一波
     * level : 1
     */
    private transient Boolean isSelected;
    private String id;
    private long createTime;
    private String name;
    private int level;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
