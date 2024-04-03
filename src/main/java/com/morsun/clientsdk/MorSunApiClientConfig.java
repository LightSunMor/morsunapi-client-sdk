package com.morsun.clientsdk;

import com.morsun.clientsdk.client.MorApiClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @package_name: com.morsun.clientsdk
 * @date: 2023/7/28
 * @week: 星期五
 * @message:
 * @author: morSun
 */
@Configuration
@ConfigurationProperties("morsun.client")
@Data
@ComponentScan
public class MorSunApiClientConfig {

    private String accessKey;
    private String secretKey;

    /**
     * 网关
     */
    private String host;

    @Value("${morsun.client.body:default-morsun}")
    private String body;

    @Bean
    public MorApiClient morApiClient()
    {
        MorApiClient client = new MorApiClient(accessKey,secretKey);
        client.setBody(body);
        return client;
    }

}
