/*
 *
 *  *  Copyright (C) 2018  老干爹<2014314038@qq.com>
 *
 *  *  Boot-Platform 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老干爹传播人，奖励1000。
 *  *  老干爹将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.github.wxiaoqi.cloud.auth.module.oauth.config;

import com.github.wxiaoqi.cloud.auth.configuration.SecurityConfiguration;
import com.github.wxiaoqi.cloud.auth.module.oauth.service.OauthUserDetailsService;
import com.github.wxiaoqi.cloud.auth.configuration.SecurityConfiguration;
import com.github.wxiaoqi.cloud.auth.module.oauth.service.OauthUserDetailsService;
import com.github.wxiaoqi.cloud.common.util.Sha256PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 老干爹 on 2017/8/11.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OauthUserDetailsService oauthUserDetailsService;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .authorizeRequests().antMatchers(securityConfiguration.getIgnoreResources().toArray(new String[]{}))
                .permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll();
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(oauthUserDetailsService).passwordEncoder(new Sha256PasswordEncoder());
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}