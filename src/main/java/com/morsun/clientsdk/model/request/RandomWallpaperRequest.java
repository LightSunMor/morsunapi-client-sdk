package com.morsun.clientsdk.model.request;

import com.morsun.clientsdk.model.enums.RequestMethodEnum;
import com.morsun.clientsdk.model.params.RandomWallpaperParams;
import com.morsun.clientsdk.model.response.RandomWallpaperResponse;
import lombok.experimental.Accessors;

/**
 * @package_name: com.morsun.clientsdk.model.request
 * @date: 2024/3/18
 * @week: 星期一
 * @message: 随机壁纸请求
 * @author: morSun
 */
@Accessors(chain = true)
public class RandomWallpaperRequest extends BaseRequest<RandomWallpaperParams, RandomWallpaperResponse>{

    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }

    @Override
    public String getPath() {
        return "/randomWallpaper";
    }

    @Override
    public Class<RandomWallpaperResponse> getResponseClass() {
        return RandomWallpaperResponse.class;
    }
}
