package com.atguigu.springcloud;

import com.atguigu.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
 * 80访问的支付微服务是CLOUD-PAYMENT-SERVICE，配置加了一个MySelfRule，并启用里面的 随机 负载均衡
 * @author zhangqiang
 * @create 2021-01-16 23:05
 */
@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name = "cloud-payment-service", configuration = MySelfRule.class)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
