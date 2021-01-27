package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangqiang
 * @create 2021-01-16 20:56
 */
@Slf4j
@RestController
public class PaymentController {
    /**
     * 服务发现 获取服务信息
     */
    @Resource
    private DiscoveryClient discoveryClient;
    /**
     * 端口
     * 查看负载均衡的郊果
     */
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getById(@PathVariable("id") Long id) {
        log.info("===>getById: {}; serverPort: {}", id, serverPort);
        Payment payment = paymentService.getById(id);
        if (payment != null){
            return new CommonResult(200, "查询成功："+serverPort, payment);
        } else {
            log.error("没有查询到记录，查询ID：{}", id);
            return new CommonResult(300, "没有查询到记录，查询ID：" + id, null);
        }
    }

    @PostMapping(value = "/payment/insert")
    public CommonResult insert(@RequestBody Payment payment) {
        log.info("===>insert: {}; serverPort: {}", payment, serverPort);
        int i = paymentService.insert(payment);
        if (i > 0) {
            return new CommonResult(200, "插入数据成功："+serverPort, i);
        } else {
            log.error("插入失败，ID：{}", payment);
            return new CommonResult(300, "插入数据失败", null);
        }
    }

    /**
     * 服务发现
     *
     * @return
     */
    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        log.info("==>discovery");
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("service===>" + service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-order-service");
        for (ServiceInstance instance : instances) {
            log.info("instance===>" + instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getInstanceId());

        }
        return this.discoveryClient;
    }

    /**
     * 测试 自己手写的负载均衡
     * @return
     */
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    /**
     * 测试 OpenFeign超时控制，OpenFeign默认超时时间是1秒
     * 这里 业务逻辑处理正确，但是需要耗费3秒钟
     * @return
     */
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout()
    {
        // 业务逻辑处理正确，但是需要耗费3秒钟
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        return serverPort;
    }

}
