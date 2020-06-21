package com.github.wxiaoqi.cloud.generator;

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
@EnableFeignClients({"com.github.wxiaoqi.cloud.generator.feign"})
@ComponentScan({"com.github.wxiaoqi.cloud.common","com.github.wxiaoqi.cloud.generator"})
@EnableSwagger2Doc
@MapperScan("com.github.wxiaoqi.cloud.generator.mapper")
public class GeneratorServerBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorServerBootstrap.class, args);
    }
}
