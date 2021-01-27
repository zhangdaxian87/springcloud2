package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 替换 负载均衡规则，默认的是论询，
 * @author zhangqiang
 * @create 2021-01-18 13:10
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule(){
        // 自定义负载均衡为 随机
        return new RandomRule();//定义为随机
    }
}
