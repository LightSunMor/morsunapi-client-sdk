package com.morsun.clientsdk.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @package_name: com.morsun.clientsdk.model.response
 * @date: 2024/3/18
 * @week: 星期一
 * @message:
 * @author: morSun
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RandomWallpaperResponse extends ResultResponse implements Serializable {

    private static final long serialVersionUID = 3923695979933067228L;
    private String imgurl;
}
