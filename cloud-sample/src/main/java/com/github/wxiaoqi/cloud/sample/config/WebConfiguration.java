package com.github.wxiaoqi.cloud.sample.config;
import com.github.wxiaoqi.cloud.common.handler.GlobalExceptionHandler;
import com.github.wxiaoqi.cloud.common.interceptor.TenantInterceptor;
import com.github.wxiaoqi.cloud.common.interceptor.UserAuthInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 老干爹 on 2017/9/8.
 */
@Configuration("cloudWebConfig")
@Primary
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTenantInterceptor()).addPathPatterns(
                "/sample/{tenant}/depart/**");
        registry.addInterceptor(getUserAuthRestInterceptor()).addPathPatterns("/**").excludePathPatterns(securityConfiguration.getIgnoreResources().toArray(new String[]{}));
    }


    @Bean
    UserAuthInfoInterceptor getUserAuthRestInterceptor() {
        return new UserAuthInfoInterceptor();
    }

    @Bean
    TenantInterceptor getTenantInterceptor() {
        return new TenantInterceptor();
    }

}
