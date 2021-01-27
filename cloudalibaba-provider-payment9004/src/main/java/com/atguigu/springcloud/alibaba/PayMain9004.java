package com.atguigu.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhangqiang
 * @create 2021-01-24 14:56
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PayMain9004 {
    public static void main(String[] args) {
        SpringApplication.run(PayMain9004.class, args);
    }
}
