package com.morsun.clientsdk.model.request;

import com.morsun.clientsdk.model.enums.RequestMethodEnum;
import com.morsun.clientsdk.model.params.WeatherParams;
import com.morsun.clientsdk.model.response.ResultResponse;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @package_name: com.morsun.clientsdk.model.request
 * @date: 2024/3/18
 * @week: 星期一
 * @message:
 * @author: morSun
 */
@Accessors(chain = true)
public class WeatherRequest extends BaseRequest<WeatherParams, ResultResponse> {
    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }

    @Override
    public String getPath() {
        return "/weatherInfo";
    }

    @Override
    public Class<ResultResponse> getResponseClass() {
        return ResultResponse.class;
    }
}
