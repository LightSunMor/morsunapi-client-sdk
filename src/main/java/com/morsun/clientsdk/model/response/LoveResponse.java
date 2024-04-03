package com.morsun.clientsdk.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @package_name: com.morsun.clientsdk.model.response
 * @date: 2024/3/15
 * @week: 星期五
 * @message:
 * @author: morSun
 */
@Data
@EqualsAndHashCode(callSuper = true)
//，用于自动生成 equals 和 hashCode 方法，并且会调用父类的相应方法。callSuper = true: 表示在生成的 equals 和 hashCode 方法中会调用父类的相应方法，以确保包含了父类的属性。
public class LoveResponse extends ResultResponse{
    private static final long serialVersionUID = -1038984103811824271L;
    private String text;
}
