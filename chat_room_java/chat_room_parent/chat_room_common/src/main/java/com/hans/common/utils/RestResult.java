package com.hans.common.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 公共返回类
 *
 * @author :  Hansing
 * @TODO: 2020/5/6
 */
@Data
public class RestResult implements Serializable {

    private static final long serialVersionUID = 5604397944150888077L;


    public static final int SUCCESS = 0;
    public static final int ERROR = -1;

    private int code;

    private String message;

    private Object data;

    public RestResult() {
    }

    public static RestResult buildSuccess() {
        RestResult restResult = new RestResult();
        restResult.setCode(SUCCESS);
        return restResult;
    }

    public static RestResult buildError() {
        RestResult restResult = new RestResult();
        restResult.setCode(ERROR);
        return restResult;
    }

    public static RestResult buildSuccess(String msg) {
        RestResult restResult = new RestResult();
        restResult.setCode(SUCCESS);
        restResult.setMessage(msg);
        return restResult;
    }

    public static RestResult buildError(String msg) {
        RestResult restResult = new RestResult();
        restResult.setCode(ERROR);
        restResult.setMessage(msg);
        return restResult;
    }

    public static RestResult buildSuccess(Object data) {
        RestResult restResult = new RestResult();
        restResult.setCode(SUCCESS);
        restResult.setData(data);
        return restResult;
    }

    public static RestResult buildSuccess(String msg, Object data) {
        RestResult restResult = new RestResult();
        restResult.setCode(SUCCESS);
        restResult.setData(data);
        restResult.setMessage(msg);
        return restResult;
    }



}
