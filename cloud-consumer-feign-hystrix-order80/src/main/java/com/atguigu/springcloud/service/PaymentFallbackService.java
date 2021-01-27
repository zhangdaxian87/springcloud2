package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

/**
 * 服务降级在Service接口实现类中实现
 * @author zhangqiang
 * @create 2021-01-19 18:39
 */
@Service
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "===> 服务降级--- PaymentFallbackService fallback paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "===> 服务降级--- PaymentFallbackService fallback paymentInfo_TimeOut";
    }
}
