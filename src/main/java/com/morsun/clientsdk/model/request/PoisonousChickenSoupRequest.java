package com.morsun.clientsdk.model.request;

import com.morsun.clientsdk.model.enums.RequestMethodEnum;
import com.morsun.clientsdk.model.params.PoisonousChickenSoupParams;
import com.morsun.clientsdk.model.response.PoisonousChickenSoupResponse;

/**
 * @package_name: com.morsun.clientsdk.model.request
 * @date: 2024/3/16
 * @week: 星期六
 * @message:
 * @author: morSun
 */
public class PoisonousChickenSoupRequest extends BaseRequest<PoisonousChickenSoupParams,PoisonousChickenSoupResponse>{

    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }

    @Override
    public String getPath() {
        return "/poisonousChickenSoup";
    }

    @Override
    public Class<PoisonousChickenSoupResponse> getResponseClass() {
        return PoisonousChickenSoupResponse.class;
    }
}
