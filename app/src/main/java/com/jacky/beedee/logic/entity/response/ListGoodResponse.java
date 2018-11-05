package com.jacky.beedee.logic.entity.response;

import com.jacky.beedee.logic.entity.Good;

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
    private List<Good> content;
    private int page;
    private int size;
    private int total;
    private boolean last;
    private boolean first;

    public List<Good> getContent() {
        return content;
    }

    public void setContent(List<Good> content) {
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
