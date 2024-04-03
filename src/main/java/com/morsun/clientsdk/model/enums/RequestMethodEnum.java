package com.morsun.clientsdk.model.enums;

/**
 * @package_name: com.morsun.clientsdk.model.enums
 * @date: 2024/3/15
 * @week: 星期五
 * @message: 请求方法枚举，get OR post
 * @author: morSun
 */
public enum RequestMethodEnum {
    /**
     * GET请求
     */
    GET("GET", "GET"),
    POST("POST", "POST");
    private final String text;
    private final String value;

    RequestMethodEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
