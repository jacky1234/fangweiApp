package com.jacky.labeauty.logic.entity.module;

import java.io.Serializable;

public class MyEntityPrize implements Serializable {

    /**
     * id : 5cb06d992329465344a4cb38
     * createTime : 1555066266000
     * updateTime : 1555066266000
     * name : PS4
     * thumb : http://localhost:9211/oss/get/201904/5cb06d932329465344a4cb36_s6183_w200_h200.png
     * stockNumber : 0
     * stockTotal : 0
     * disabled : false
     * prizeLogId : 5cb06d992329465344a4cb48
     * sendTime : 1556604636000
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String name;
    private String thumb;
    private int stockNumber;
    private int stockTotal;
    private boolean disabled;
    private String prizeLogId;
    private long sendTime;

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public int getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(int stockTotal) {
        this.stockTotal = stockTotal;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getPrizeLogId() {
        return prizeLogId;
    }

    public void setPrizeLogId(String prizeLogId) {
        this.prizeLogId = prizeLogId;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
