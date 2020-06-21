package com.github.wxiaoqi.cloud.gate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Created by Steven on 2019/10/27.
 */
@EnableWebFluxSecurity
public class ResourceServerConfigurer {

    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers(startWith.split(",")).permitAll()
                .anyExchange().authenticated();
        http.csrf().disable();
        http.oauth2ResourceServer().jwt();

        return http.build();
    }
}
