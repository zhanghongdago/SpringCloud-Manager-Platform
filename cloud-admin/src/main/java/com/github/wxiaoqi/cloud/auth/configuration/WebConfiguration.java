/*
 *  Copyright (C) 2018  老干爹<2014314038@qq.com>

 *  Boot-Platform 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老干爹传播人，奖励1000。
 *  老干爹将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.wxiaoqi.cloud.auth.configuration;

import com.github.wxiaoqi.cloud.common.interceptor.TenantInterceptor;
import com.github.wxiaoqi.cloud.auth.interceptor.UserAuthRestInterceptor;
import com.github.wxiaoqi.cloud.auth.module.wf.activiti.modeler.JsonpCallbackFilter;
import com.github.wxiaoqi.cloud.common.handler.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 老干爹 on 2017/9/8.
 */
@Configuration("admimWebConfig")
@Primary
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Bean
    public JsonpCallbackFilter filter(){
        return new JsonpCallbackFilter();
    }

    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ArrayList<String> commonPathPatterns = getExcludeCommonPathPatterns();
        registry.addInterceptor(getTenantInterceptor()).addPathPatterns(
                "/admin/{tenant}/depart/**",
                "/admin/{tenant}/gateLog/**",
                "/admin/{tenant}/group/**",
                "/admin/{tenant}/position/**",
                "/admin/{tenant}/user/**");
        registry.addInterceptor(getUserAuthRestInterceptor()).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
    }


    @Bean
    UserAuthRestInterceptor getUserAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }

    @Bean
    TenantInterceptor getTenantInterceptor() {
        return new TenantInterceptor();
    }

    private ArrayList<String> getExcludeCommonPathPatterns() {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(securityConfiguration.getIgnoreResources());
        String[] urls = {
                "/oauth/**",
                "/login"
        };
        Collections.addAll(list, urls);
        return list;
    }
}
