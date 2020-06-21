package com.github.wxiaoqi.cloud.auth.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
@ConfigurationProperties(prefix = "security")
public class SecurityConfiguration {
    private List<String> ignoreResources;
}