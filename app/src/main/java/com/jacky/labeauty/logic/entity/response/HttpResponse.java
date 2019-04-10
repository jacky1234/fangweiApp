package com.jacky.labeauty.logic.entity.response;

public class HttpResponse<T> extends HttpResponseSource {
    private T data;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
