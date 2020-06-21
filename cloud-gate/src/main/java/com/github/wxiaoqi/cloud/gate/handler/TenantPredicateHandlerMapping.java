package com.github.wxiaoqi.cloud.gate.handler;

import com.github.wxiaoqi.cloud.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.cloud.gateway.handler.FilteringWebHandler;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author laogandie
 * @create 2019/12/15.
 */
@Component
@Primary
public class TenantPredicateHandlerMapping extends RoutePredicateHandlerMapping {

    public TenantPredicateHandlerMapping(FilteringWebHandler webHandler, RouteLocator routeLocator, GlobalCorsProperties globalCorsProperties, Environment environment) {
        super(webHandler, routeLocator, globalCorsProperties, environment);
    }

    @Override
    protected Mono<?> getHandlerInternal(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        List<String> tenantFlag = request.getHeaders().get(RequestHeaderConstants.TENANT);
        if (tenantFlag != null && tenantFlag.size() > 0) {
//            String value = request.getPath().value();
//            String prefix = value.substring(0, value.indexOf("/", 1));
//            String[] split = value.split("/");
//            String routeUrl = value.substring(value.indexOf("/", prefix.length() + 1));
//            if (split.length > 2) {
//                String tenant = split[2];
//                String path = prefix + routeUrl;
//                ServerHttpRequest build = request.mutate().path(path).header(RequestHeaderConstants.TENANT, tenant).build();
//                return super.getHandlerInternal(serverWebExchange.mutate().request(build).build());
//            }
            BaseContextHandler.setTenantID(tenantFlag.get(0));
        }
        return super.getHandlerInternal(serverWebExchange);
    }
}