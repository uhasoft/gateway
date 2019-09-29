package com.uhasoft.gateway;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Weihua
 * @since 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@NacosPropertySource(dataId = "gateway", autoRefreshed = true)
public class Gateway {

    public static void main(String[] args) {
        SpringApplication.run(Gateway.class, args);
    }

}
