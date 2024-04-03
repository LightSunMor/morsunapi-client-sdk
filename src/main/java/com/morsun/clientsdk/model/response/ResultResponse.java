package com.morsun.clientsdk.model.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @package_name: com.morsun.clientsdk.model.response
 * @date: 2024/3/15
 * @week: 星期五
 * @message: 基础API响应体
 * @author: morSun
 */
@Data
@NoArgsConstructor
public class ResultResponse {
    private static final long serialVersionUID = -6486005224268968744L;
    // 使用Map的原因是 它本身就跟json格式相似 （直接接收来接目标接口返回的json，到时候使用JSONUtil工具转为Map）
    private Map<String, Object> data = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
