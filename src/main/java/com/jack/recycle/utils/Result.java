package com.jack.recycle.utils;

import lombok.Data;

@Data
public class Result<T> {
    private Integer status;
    private String message;
    private T data;

    private String token;

    public Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Result(Integer status, String message, T data,String token) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.token = token;
    }

    public Result() {

    }
}
