package com.github.wxiaoqi.cloud.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Steven on 2019/10/27.
 */
@EnableWebSecurity
@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(securityConfiguration.getIgnoreResources().toArray(new String[]{})).permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();
        http.oauth2ResourceServer().jwt();

    }


}
