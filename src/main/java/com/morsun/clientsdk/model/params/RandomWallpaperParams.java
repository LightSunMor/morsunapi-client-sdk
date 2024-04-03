package com.morsun.clientsdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @package_name: com.morsun.clientsdk.model.params
 * @date: 2024/3/18
 * @week: 星期一
 * @message: 随机壁纸请求参数
 * @author: morSun
 */
@Data
@Accessors(chain = true)
public class RandomWallpaperParams implements Serializable {

    private static final long serialVersionUID = -7701607394218252417L;
    //输出分类meizi、dongman、fengjing、suiji，为空随机输出
    private String lx;
    //输出壁纸端mobile、pc、zsy默认为pc
    private String method;
}
