package com.morsun.clientsdk.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @package_name: com.morsun.clientsdk.model.response
 * @date: 2024/3/16
 * @week: 星期六
 * @message:
 * @author: morSun
 */
@Data
@EqualsAndHashCode(callSuper = true) // 会调用父类ResultResponse的hashCode和equals
public class PoisonousChickenSoupResponse extends ResultResponse implements Serializable {
    private static final long serialVersionUID = -6467312483425078539L;
    private String text;
}
