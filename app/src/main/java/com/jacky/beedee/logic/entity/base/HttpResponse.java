package com.jacky.beedee.logic.entity.base;

public class HttpResponse<T> {
    private int code;
    private String message;
    private T t;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getT() {
        return t;
    }
}
