package com.morsun.clientsdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @package_name: com.morsun.clientsdk.model.params
 * @date: 2024/3/18
 * @week: 星期一
 * @message: IP信息请求参数
 * @author: morSun
 */
@Data
@Accessors(chain = true)
public class IpInfoParams implements Serializable {
    private static final long serialVersionUID = -8313231303926094552L;
    private String ip;
}