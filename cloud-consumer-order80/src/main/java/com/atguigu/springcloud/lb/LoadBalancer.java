package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 手写一个论询 负载均衡算法
 * @author zhangqiang
 * @create 2021-01-18 13:38
 */
public interface LoadBalancer {
    ServiceInstance instance (List<ServiceInstance> serviceInstances);
}
