package com.morsun.clientsdk.model.response;

import lombok.Data;

/**
 * @package_name: com.morsun.clientsdk.model.response
 * @date: 2024/3/15
 * @week: 星期五
 * @message: 错误响应体
 * @author: morSun
 */
@Data
public class ErrorResponse {
    private String message;
    private int code;
}
