package com.jacky.beedee.logic.entity.response;

import com.jacky.beedee.logic.entity.module.Favorite;

import java.util.List;

public class FavoriteResponse {


    /**
     * page : 0
     * size : 10
     * total : 1
     * last : true
     * first : true
     */
    private List<Favorite> content;
    private int page;
    private int size;
    private int total;
    private boolean last;
    private boolean first;

    public List<Favorite> getContent() {
        return content;
    }

    public void setContent(List<Favorite> content) {
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
