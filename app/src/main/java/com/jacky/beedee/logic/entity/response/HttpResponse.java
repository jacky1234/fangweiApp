package com.jacky.beedee.logic.entity.response;

public class HttpResponse<T> extends HttpResponseSource {
    private T t;

    public T getData() {
        return t;
    }
}
