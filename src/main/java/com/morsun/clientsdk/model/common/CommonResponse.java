package com.morsun.clientsdk.model.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @package_name: com.morsun.clientsdk.model.common
 * @date: 2024/3/6
 * @week: 星期三
 * @message: 所有接口调用结果 统一返回类型
 * @author: morSun
 */
@Data
public class CommonResponse<T> implements Serializable {
    private int code;

    private T data;

    private String message;

    public CommonResponse() {
    }

    public CommonResponse(T data) {
        this.code=0;
        this.message="";
        this.data=data;
    }
    public CommonResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public CommonResponse(int code, T data) {
        this(code, data, "");
    }

}
