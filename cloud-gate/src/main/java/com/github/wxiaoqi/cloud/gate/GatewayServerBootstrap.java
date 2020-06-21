package com.github.wxiaoqi.cloud.gate;

import com.github.wxiaoqi.cloud.gate.utils.DBLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ace
 * @create 2018/3/12.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.github.wxiaoqi.cloud.common.feign","com.github.wxiaoqi.cloud.gate"})
//@EnableAceAuthClient
@EnableFeignClients({"com.github.wxiaoqi.cloud.auth.client.feign", "com.github.wxiaoqi.cloud.gate.feign"})
public class GatewayServerBootstrap {
    public static void main(String[] args) {
        DBLog.getInstance().start();
        SpringApplication.run(GatewayServerBootstrap.class, args);
    }
}
