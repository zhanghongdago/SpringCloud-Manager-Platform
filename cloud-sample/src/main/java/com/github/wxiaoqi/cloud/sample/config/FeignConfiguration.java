package com.github.wxiaoqi.cloud.sample.config;


import com.github.wxiaoqi.cloud.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author ace
 * @date 2017/9/12
 */
public class FeignConfiguration {
    @Bean
    RequestInterceptor getTokenInterceptor(){
        return requestTemplate -> {
            requestTemplate.header(RequestHeaderConstants.TENANT, BaseContextHandler.getTenantID());
            requestTemplate.header(RequestHeaderConstants.AUTHORIZATION, BaseContextHandler.getToken());
        };
    }
}