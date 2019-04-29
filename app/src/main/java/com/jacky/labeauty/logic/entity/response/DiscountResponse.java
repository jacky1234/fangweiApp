package com.jacky.labeauty.logic.entity.response;

import com.jacky.labeauty.logic.entity.module.MyDiscount;

import java.util.List;

public class DiscountResponse {

    /**
     * message : ok
     * page : 0
     * size : 10
     * total : 1
     * last : true
     * first : true
     */

    private String message;
    private int page;
    private int size;
    private int total;
    private boolean last;
    private boolean first;
    private List<MyDiscount> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public List<MyDiscount> getData() {
        return data;
    }

    public void setData(List<MyDiscount> data) {
        this.data = data;
    }
}
