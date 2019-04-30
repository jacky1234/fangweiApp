package com.jacky.labeauty.logic.entity.request;

public class IntegralRequest {
    //IN：收入，OUT：支出，空：全部
    private String direction;
    private int page;
    private int size = 10;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
