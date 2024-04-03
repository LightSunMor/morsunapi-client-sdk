package com.morsun.clientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @package_name: com.morsun.utils
 * @date: 2023/7/27
 * @week: 星期四
 * @message: 签名算法
 * @author: morSun
 */
public class SignUtil {
    public static String genSign(String body,String secretKey)
    {
        // todo 升级加密算法，md5算法计算时间过长
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String sign = md5.digestHex(body + "." + secretKey);
        return sign;
    }
}
