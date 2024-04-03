package com.morsun.clientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.morsun.clientsdk.exception.ApiException;
import com.morsun.clientsdk.exception.ErrorCode;
import com.morsun.clientsdk.model.User;
import com.morsun.clientsdk.model.common.CommonResponse;
import com.morsun.clientsdk.model.params.IpInfoParams;
import com.morsun.clientsdk.model.params.RandomWallpaperParams;
import com.morsun.clientsdk.model.request.*;
import com.morsun.clientsdk.model.response.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import static com.morsun.clientsdk.utils.SignUtil.genSign;


/**
 * @package_name: com.morsun.client
 * @date: 2023/7/26
 * @week: 星期三
 * @message:
 * @author: morSun
 */
@Data
@Slf4j
public class MorApiClient {
    public static final String GET = "get";
    public static final String POST = "post";

    private String accessKey;
    private String secretKey;
    // 特殊标识
    private String body;


    private String InfoAsciiBodySet(String body) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i= 0 ;i<body.length();i++)
        {
            stringBuilder.append((int)body.charAt(i));
        }
        return stringBuilder.toString();
    }

    public void setBody(String body) {
        this.body = InfoAsciiBodySet(body);
    }

    private static final String GATE_WAY_HOST ="http://localhost:8090/api-m";

    public MorApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public MorApiClient(String accessKey, String secretKey, String body) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.body = body;
    }

    // region 自己第一次规划sdk思路
    //思路：按业务去划分SDK，比如支付SDK，短信SDK等，因为同样的业务他们的接口是非常相似的，只是有细微的参数区别

    //  测试网关会不会接收到 name 这样的查询参数
    public String getNameByGet(String name)
    {
        String s = HttpUtil.get(GATE_WAY_HOST+"/name?name=" + name);
        return s;
    }

    public String getUsernameByPost(@RequestBody User user)
    {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post(GATE_WAY_HOST+"/name/user")
                .addHeaders(getHeaders())
                .body(json).execute();
        System.out.println(response.getStatus());
        return response.body();
    }

    private Map<String, String> getHeaders() {
        Map<String,String> map = new HashMap<>();
        map.put("accessKey",accessKey);
        // 一定不能直接发送给别人
//        map.put("secretKey",secretKey);
        map.put("nonce", String.valueOf(RandomUtil.randomNumber()));
        map.put("body",body);
        map.put("sign",genSign(body,secretKey));
        map.put("timestamp",String.valueOf(System.currentTimeMillis()));
        return map;
    }
    // endregion

    // 新的———— todo 未拆分到各个类中！！
    // api系统调用接口使用的统一方法
    /**
     *  网关host
     *  区分线上和线下
     *   online downLine 上下线不同
     */
    private String gatewayHost ="http://localhost:8090/api-m"; //todo 线下网关

    /**
     *  获得响应数据
     * @param request
     * @param <O>
     * @param <T>
     * @return
     * @throws ApiException
     */
    // T 之前的<> 是在定义泛型，以便方法中使用
    public <O, T extends ResultResponse> T request(BaseRequest<O, T> request) throws ApiException {
        try {
            return res(request);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
    }


    /**
     * 获取响应数据
     *
     * @param request 要求
     * @return {@link T} 基于ResultResponse的各种response
     * @throws ApiException 业务异常
     */
    public <O, T extends ResultResponse> T res(BaseRequest<O, T> request) throws ApiException {
        if (!StringUtils.hasText(this.getAccessKey())||!StringUtils.hasText(this.getSecretKey())) {
            throw new ApiException(ErrorCode.NO_AUTH_ERROR, "请先配置密钥AccessKey/SecretKey");
        }
        // 结果Response
        T rsp;
        try {
            // 反射,动态创建对应响应体实例
            Class<T> clazz = request.getResponseClass();
            // 暴力解除限制
            rsp = clazz.newInstance();
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
        // 执行请求
        HttpResponse httpResponse = doRequest(request);
        String body = httpResponse.body();
        Map<String, Object> data = new HashMap<>();
        if (httpResponse.getStatus() != 200) {
            ErrorResponse errorResponse = JSONUtil.toBean(body, ErrorResponse.class);
            data.put("errorMessage", errorResponse.getMessage());
            data.put("code", errorResponse.getCode());
        } else {
            try {
                // 尝试解析为JSON对象，装进data结果，准备返回给前端
                data = new Gson().fromJson(body, new TypeToken<Map<String, Object>>() {}.getType());
            } catch (JsonSyntaxException e) {
                // 解析失败，将body作为普通字符串处理 (todo 知识点 兼容性处理)
                data.put("value", body);
            }
        }
        rsp.setData(data);
        return rsp;
    }

    /***
     *  执行请求
     * @param request
     * @param <O>
     * @param <T>
     * @return
     * @throws ApiException
     */
    private <O, T extends ResultResponse> HttpResponse doRequest(BaseRequest<O, T> request) throws ApiException {
        //todo 知识点： 回收 getHttpRequestByRequestMethod中开启的资源.这些资源必须实现 AutoCloseable 接口。在 try 块结束时，系统会自动关闭这些资源。
        // 而且如果在关闭资源时抛出了异常，它会被抑制，并且在 try 块或 catch 块中的异常不会被覆盖，可以通过 Throwable.getSuppressed() 方法来获取被抑制的异常信息。
        try (HttpResponse httpResponse = getHttpRequestByRequestMethod(request).execute()) {  //😏😏 execute执行请求调用了，此时向网关gateway进攻
            return httpResponse;
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
    }

    /***
     * 获得HttpRequest
     * @param request
     * @param <O>
     * @param <T>
     * @return
     * @throws ApiException
     */
    private <O, T extends ResultResponse> HttpRequest getHttpRequestByRequestMethod(BaseRequest<O, T> request) throws ApiException {
        if (ObjectUtils.isEmpty(request)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求参数错误");
        }
        String path = request.getPath().trim();
        String method = request.getMethod().trim().toUpperCase();

        if (ObjectUtils.isEmpty(method)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求方法不存在");
        }
        if (!StringUtils.hasText(path)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求路径不存在");
        }

        // 在网关做一个转发，让其调用真正的服务提供host，表面只能看到网关的host
        if (path.startsWith(gatewayHost)) {
            path = path.substring(gatewayHost.length()); // 拿到除网关以外的其他路径
        }

        log.info("请求方法：{}，请求路径：{}，请求参数：{}", method, path, request.getRequestParams());

        // !!💥 真正调用的时候
        HttpRequest httpRequest;
        // 向网关发出请求
        switch (method) {
            case "GET": {
                // splicingGetRequest 拼接get请求
                httpRequest = HttpRequest.get(splicingGetRequest(request, path));
                break;
            }
            case "POST": {
                httpRequest = HttpRequest.post(gatewayHost + path);
                break;
            }
            default: {
                throw new ApiException(ErrorCode.OPERATION_ERROR, "不支持该请求");
            }
        }
        String bodyJsonStr = JSONUtil.toJsonStr(request.getRequestParams());
        return httpRequest.addHeaders(getHeaders()).body(bodyJsonStr);
    }

    /**
     *   拼接GET请求 ！！！！ 值得收藏
     * @param request
     * @param path
     * @param <O>
     * @param <T>
     * @return
     */
    private <O, T extends ResultResponse> String splicingGetRequest(BaseRequest<O, T> request, String path) {
        StringBuilder urlBuilder = new StringBuilder(gatewayHost);
        // urlBuilder最后是/结尾 且 path以/开头的情况下，去掉urlBuilder结尾的/
        if (urlBuilder.toString().endsWith("/") && path.startsWith("/")) {
            urlBuilder.setLength(urlBuilder.length() - 1);
        }
        urlBuilder.append(path);
        if (!request.getRequestParams().isEmpty()){
            // 参数不为空，开始拼接url
            urlBuilder.append("?");
            for (Map.Entry<String, Object> entry : request.getRequestParams().entrySet()) {
                String key = entry.getKey();
                String value= entry.getValue().toString();
                urlBuilder.append(key).append("=").append(value).append("&");
            }
            // 参数拼接完成后，删除最后一个 &
            urlBuilder.deleteCharAt(urlBuilder.length()-1);
        }
        log.info("此次GET 请求路径：{}",urlBuilder);
        return urlBuilder.toString();
    }


    //----- 提供给第三方使用的接口调用方法 ，待增加

    /**
     *  毒鸡汤
     * @return
     * @throws ApiException
     */
    public PoisonousChickenSoupResponse getPoisonousChickenSoup() throws ApiException {
        PoisonousChickenSoupRequest request = new PoisonousChickenSoupRequest();
        return request(request);
    }


    /**
     *  获取Ip信息
     * @param params
     * @return
     * @throws ApiException
     */
    public ResultResponse getIpInfo(IpInfoParams params) throws ApiException{
        IpInfoRequest request = new IpInfoRequest();
        request.setRequestParams(params);
        return request(request);
    }

    /**
     *  随机情话
     * @return
     * @throws ApiException
     */
    public LoveResponse randomLoveTalk() throws ApiException{
        LoveRequest request = new LoveRequest();
        return request(request);
    }

    /**
     *  随机壁纸
     * @param params
     * @return
     * @throws ApiException
     */
    public RandomWallpaperResponse getRandomWallpaper(RandomWallpaperParams params) throws ApiException{
        RandomWallpaperRequest request = new RandomWallpaperRequest();
        request.setRequestParams(params);
        return request(request);
    }





}
