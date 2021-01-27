package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 全局服务降级 @DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
 * @author zhangqiang
 * @create 2021-01-19 12:00
 */
@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;
    /**
     * 正常访问
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_OK(id);
        log.info("===>result: {}", result);
        return result;
    }

    /**
     * 超时访问
     * 给方法自定义 服务降级
     * paymentInfo_TimeOutHandler 服务降级 FallBack方法
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod="paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
    })
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        log.info("===> result: {}", result);
        return result;
    }

    /**
     * 正常访问
     * 全局 服务降级 @HystrixCommand
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/exception/{id}")
    @HystrixCommand
    public String paymentInfo_Exception(@PathVariable("id") Integer id) {
        log.info("===>result id: {}", id);
        int i = 10/id;
        return "OK";
    }

    /**
     * FallBack 指定的方法
     *
     * @param id
     * @return
     */
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池: " + Thread.currentThread().getName() + ";  paymentInfo_OK: id=" + id + " \t====对不起，80" +
                "网络忙，请稍后再试===>";
    }

    /**
     * FallBack 全局异常处理方法
     * 全局FallBack方法，在需要用用到的服务降级 方法上加 @HystrixCommand
     * @return
     */
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试。";
    }
}
