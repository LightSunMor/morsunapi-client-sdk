package com.morsun.clientsdk.model.request;

import com.morsun.clientsdk.model.enums.RequestMethodEnum;
import com.morsun.clientsdk.model.response.LoveResponse;

/**
 * @package_name: com.morsun.clientsdk.model.request
 * @date: 2024/3/15
 * @week: 星期五
 * @message: 随机情话请求体
 * @author: morSun
 */
// 不同的请求，有不同的返回参数，如果想好看一点，这样做不错。但是麻烦。如果只返回String类型（万能），简单但是请求结果收到之后不太好看
public class LoveRequest extends BaseRequest<String, LoveResponse>{
    @Override
    public String getPath() {
        return "/loveTalk";
    }

    /**
     * 获取响应类
     *
     * @return {@link Class}<{@link LoveResponse}>
     */
    @Override
    public Class<LoveResponse> getResponseClass() {
        return LoveResponse.class;
    }


    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
