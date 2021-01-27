package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangqiang
 * @create 2021-01-23 16:51
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping(value = "/testA")
    public String testA() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("test RT");
        return "===>testA";
    }

    @GetMapping(value = "/testB")
    public String testB() {
        return "===>testB";
    }

    // 降级规则 RT
    @GetMapping(value = "/testRT")
    public String testRT() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("test RT");
        return "===>testA";
    }

    //降级规则 异常比例
    @GetMapping(value = "/testYCBL")
    public String testYCBL() {
        int age = 10/0;

        log.info("test /testYCBL");
        return "===>/testYCBL";
    }

    //降级规则 异常数
    @GetMapping(value = "/testYCS")
    public String testYCS() {
        int age = 10/0;
        log.info("test /testYCS");
        return "===>/testYCS";
    }

    //热点规则
    // @SentinelResource(value = "testHotkey") Error Page异常返回前台很不友好。
    // @SentinelResource(value = "testHotkey", blockHandler = "deal_testHotkey")
    // 方法testHotkey里面第一个参数只要QPS超过每秒1秒，以上降级处理，用了我们自定义的deal_testHotkey
    @GetMapping(value = "/testHotkey")
    @SentinelResource(value = "testHotkey", blockHandler = "deal_testHotkey")
    public String testHotkey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {

        log.info("test /testHotkey");
        return "===>/testHotkey";
    }

    //热点兜底方法deal_testHotkey
    public String deal_testHotkey(String p1, String p2, BlockException exp){
        return "===> deal_testHotkey Exception";
    }
}
