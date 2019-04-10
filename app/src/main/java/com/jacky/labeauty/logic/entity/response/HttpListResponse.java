package com.jacky.labeauty.logic.entity.response;

import java.util.List;

/**
 * 2018/11/5.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class HttpListResponse<T> extends HttpResponseSource {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
