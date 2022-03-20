package com.jack.recycle.utils;

public class Result<T> {
    private Integer status;
    private String message;
    private T data;

    public Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Result() {

    }
}
