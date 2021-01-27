package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentFeignService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhangqiang
 * @create 2021-01-18 17:33
 */
@RestController
@Slf4j
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult getById(@PathVariable("id") Long id) {
        log.info("===>OrderFeignController.getById");
         return paymentFeignService.getById(id);
    }

    @PostMapping(value = "/consumer/payment/insert")
    public CommonResult insert(@RequestBody Payment payment) {
        log.info("===>OrderFeignController.insert");
        return paymentFeignService.insert(payment);
    }

    /**
     * 测试OpenFeign超时
     */
    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        log.info("===>OrderFeignController.paymentFeignTimeOut");
        return paymentFeignService.paymentFeignTimeOut();
    }
}
