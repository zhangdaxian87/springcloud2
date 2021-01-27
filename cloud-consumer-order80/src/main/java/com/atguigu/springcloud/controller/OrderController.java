package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.lb.LoadBalancer;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author zhangqiang
 * @create 2021-01-16 23:09
 */
@Slf4j
@RestController
public class OrderController {
//    private static final String PAYMENT_URL = "http://localhost:8001";
    private static final String PAYMENT_URL = "http://cloud-payment-service";

    /**
     * resTemplate(url, requestMap, ResponseBean.class)
     * REST请求地址，请求参数，HTTP响就转换的对像类型
     */
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        log.info("***Order请求ID：{}", id);
        return restTemplate.getForObject(PAYMENT_URL +"/payment/get/"+id, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/insert")
    public CommonResult<Payment> insert(Payment payment){
        log.info("***Order Insert : {}", payment);
        return restTemplate.postForObject(PAYMENT_URL+"/payment/insert", payment, CommonResult.class);
    }

    /**
     * 返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头，响应状态码，响应体等
     * @param id
     * @return
     */
    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id){
        log.info("==>method: getPayment2; id:{}", id);
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id,
                CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            log.info("===>StatusCode: {},\\t ===>tHeaders: {}"+entity.getStatusCode(),
                    entity.getHeaders());
            return  entity.getBody();
        } else {
            return new CommonResult<Payment>(300, "操作失败");
        }
    }

    /**
     * 测试自己写的负载均衡算法
     * 需要去ApplicatinContextConfig中将注解 @LoadBalanced 去掉
     */
    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentBL(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() <=0){
            return  null;
        }
        ServiceInstance serviceInstance = loadBalancer.instance(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }
}
