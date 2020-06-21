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

package com.github.wxiaoqi.cloud.auth;


import com.ace.cache.EnableAceCache;
import com.github.wxiaoqi.merge.EnableAceMerge;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 老干爹 on 2017/6/2.
 */
@SpringBootApplication
@MapperScan("com.github.wxiaoqi.cloud.auth.module.*.mapper")
@ComponentScan({"com.github.wxiaoqi.cloud.common", "com.github.wxiaoqi.cloud.auth", "org.activiti.rest.diagram", "org.activiti.rest.editor"})
@EnableSwagger2Doc
@SessionAttributes("authorizationRequest")
@EnableResourceServer
@EnableAuthorizationServer
@EnableAceMerge
@EnableAceCache
@EnableAutoConfiguration(exclude = {
//        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class,

})
@EnableDiscoveryClient
public class AdminBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(AdminBootstrap.class, args);
    }
}
