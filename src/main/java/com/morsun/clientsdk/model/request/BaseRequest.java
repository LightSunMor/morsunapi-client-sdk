package com.morsun.clientsdk.model.request;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.morsun.clientsdk.model.response.ResultResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @package_name: com.morsun.clientsdk.model.request
 * @date: 2024/3/15
 * @week: 星期五
 * @message: 基础请求体
 * @author: morSun
 */
// todo 知识点： 设计模式的应用 （每个请求的继承，以及请求中路径path的定义，而不是前端给到）
//  ☀😏 如果可以，自己设计一下，能不能再通用一点，让前端可以直接给我path
public abstract  class BaseRequest<O,T extends ResultResponse> {
    // O 是默认继承了Object ，T 继承了 ResultResponse
    private Map<String, Object> requestParams = new HashMap<>();

    /**
     * get方法
     *
     * @return {@link com.morsun.clientsdk.model.enums.RequestMethodEnum}
     */
    public abstract String getMethod();

    /**
     * 获取路径
     *
     * @return {@link String}
     */
    public abstract String getPath();

    /**
     * 获取响应类
     *  用于后续反射
     * @return {@link Class}<{@link T}>
     */
    public abstract Class<T> getResponseClass();

// 注解用于指示 Jackson 序列化时将对象中的额外属性作为 Map 返回。
// 这在处理具有动态属性的对象时很有用，这些属性在编译时可能是未知的，但在运行时可能会动态添加
    @JsonAnyGetter
    // todo 知识点：如果不添加这个注解，那么就将无法动态生成属性给到这个类，只有原本定义在类中的属性才可用 （详细见chat）
    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    // O 泛型用于填入各种类型的参数
    public void setRequestParams(O params) {
        this.requestParams = new Gson().fromJson(JSONUtil.toJsonStr(params), new TypeToken<Map<String, Object>>() {
        }.getType());
    }

}
