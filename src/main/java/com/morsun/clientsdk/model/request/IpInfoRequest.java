package com.morsun.clientsdk.model.request;

import com.morsun.clientsdk.model.enums.RequestMethodEnum;
import com.morsun.clientsdk.model.params.IpInfoParams;
import com.morsun.clientsdk.model.response.ResultResponse;
import lombok.Data;

/**
 * @package_name: com.morsun.clientsdk.model.request
 * @date: 2024/3/18
 * @week: 星期一
 * @message: ip
 * @author: morSun
 */

public class IpInfoRequest extends BaseRequest<IpInfoParams, ResultResponse>{

    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }

    @Override
    public String getPath() {
        return "/ipInfo";
    }

    @Override
    public Class<ResultResponse> getResponseClass() {
        return ResultResponse.class;
    }
}
