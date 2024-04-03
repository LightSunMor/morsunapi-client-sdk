package com.morsun.clientsdk.model.request;

import com.morsun.clientsdk.model.response.ResultResponse;
import lombok.experimental.Accessors;

/**
 * @package_name: com.morsun.clientsdk.model.request
 * @date: 2024/3/15
 * @week: 星期五
 * @message: 当前请求参数(用于平台请求)，
 * 根据 Map<String, Object> params来调用对应接口供参
 * @author: morSun
 */
@Accessors(chain = true) // 控制getter和setter的链式生成和自定义
public class CurrencyRequest extends BaseRequest<Object, ResultResponse>{
    private String method;
    private String path;

    /**
     * get方法
     *
     * @return {@link String}
     */
    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取路径
     *
     * @return {@link String}
     */
    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取响应类
     *
     * @return {@link Class}<{@link ResultResponse}>
     */
    @Override
    public Class<ResultResponse> getResponseClass() {
        return ResultResponse.class;
    }
}
