package com.jacky.labeauty.logic.entity.module;

public class MyDiscount {
    //reduceRule
    public final static String VALUE = "VALUE";
    public final static String DISCOUNT = "DISCOUNT";

    //expireRule
    public final static String FIXED = "FIXED";
    public final static String DURATION = "DURATION";

    /**
     * id : 5caed8472329462c9037a7c3
     * createTime : 1554962503000
     * updateTime : 1554962503000
     * name : 淘宝100元优惠券
     * instructions : 1、打开淘宝
     * 2、使用优惠券
     * totalNumber : 100        // 发放总量
     * reduceRule : VALUE       // 优惠形式。VALUE：直减，DISCOUNT：打折
     * reduceValue : 100
     * reduceDiscount : 7.5
     * expireRule : FIXED
     * beginTime : 1554912000000
     * endTime : 1557331200000
     * duration : 259200
     * disabled : false
     * sendTime : 1554962593000
     */

    private String id;
    private long createTime;
    private long updateTime;
    private String name;
    private String instructions;
    private int totalNumber;
    private String reduceRule;
    private int reduceValue;
    private double reduceDiscount;
    private String expireRule;
    private long beginTime;
    private long endTime;
    private int duration;
    private boolean disabled;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getReduceRule() {
        return reduceRule;
    }

    public void setReduceRule(String reduceRule) {
        this.reduceRule = reduceRule;
    }

    public int getReduceValue() {
        return reduceValue;
    }

    public void setReduceValue(int reduceValue) {
        this.reduceValue = reduceValue;
    }

    public double getReduceDiscount() {
        return reduceDiscount;
    }

    public void setReduceDiscount(double reduceDiscount) {
        this.reduceDiscount = reduceDiscount;
    }

    public String getExpireRule() {
        return expireRule;
    }

    public void setExpireRule(String expireRule) {
        this.expireRule = expireRule;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
