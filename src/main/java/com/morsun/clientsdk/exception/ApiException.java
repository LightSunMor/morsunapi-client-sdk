package com.morsun.clientsdk.exception;

/**
 * @package_name: com.morsun.clientsdk.exception
 * @date: 2024/3/15
 * @week: 星期五
 * @message: api异常
 * @author: morSun
 */
public class ApiException extends Exception{

    private static final long serialVersionUID = 2942420535500634982L;
    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
