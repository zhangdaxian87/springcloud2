package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 

/**
 * OpenFengn 日志配置
 * @author zhangqiang
 * @create 2021-01-18 19:40
 */
@Configuration
public class FeignConfig {
    /**
     * Feign 配置日志级别
     * NONE：默认的，不显示任何日志
     * BASIC：仅记录请求方法，URL，响应状态码及执行行时间
     * HEADERS：除了BASIC中定义信息之外，还有请求和响应的头信息
     * FULL：除了HEADERS定义的之外，还有请求和响应的正文及元数据
     * @return
     */
    @Bean
    public Logger.Level feingLogLevel(){
        // 请求和响应的头信息,请求和响应的正文及元数据
        return Logger.Level.FULL;
    }
}
