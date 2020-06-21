package com.github.wxiaoqi.cloud.sample;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
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
@EnableFeignClients({"com.github.wxiaoqi.cloud.sample.feign"})
@ComponentScan({"com.github.wxiaoqi.cloud.common","com.github.wxiaoqi.cloud.sample"})
@EnableSwagger2Doc
@MapperScan("com.github.wxiaoqi.cloud.sample.mapper")
public class SampleServerBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(SampleServerBootstrap.class, args);
    }
}
