package com.morsun.clientsdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @package_name: com.morsun.clientsdk.model.params
 * @date: 2024/3/18
 * @week: 星期一
 * @message: 天气查询请求参数
 * @author: morSun
 */
@Data
@Accessors(chain = true)
public class WeatherParams implements Serializable {
    private static final long serialVersionUID = 9180993086858402936L;
    // 对应城市
    private String city;
    // 默认获取一天，可配置week获取一周的信息
    private String type;
}
