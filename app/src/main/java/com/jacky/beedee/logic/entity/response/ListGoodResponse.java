package com.jacky.beedee.logic.entity.response;

import android.support.annotation.NonNull;

import com.jacky.beedee.logic.entity.GoodItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 2018/11/6.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class ListGoodResponse {

    /**
     * page : 0
     * size : 10
     * total : 1
     * last : true
     * first : true
     */
    private List<GoodItem> content;
    private int page;
    private int size;
    private int total;
    private boolean last;
    private boolean first;

    @NonNull
    public List<GoodItem> getContent() {
        if (content == null) {
            return new ArrayList<>(1);
        }

        return content;
    }

    public void setContent(List<GoodItem> content) {
        this.content = content;
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
}
