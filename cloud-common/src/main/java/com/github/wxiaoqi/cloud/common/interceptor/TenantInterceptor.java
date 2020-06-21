package com.github.wxiaoqi.cloud.common.interceptor;

import com.github.wxiaoqi.cloud.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import com.github.wxiaoqi.cloud.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import com.github.wxiaoqi.cloud.common.exception.base.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * @author 老干爹
 * @create 2018-10-24 14:17
 **/
public class TenantInterceptor implements HandlerInterceptor {

    private final static String TENANT_PATH = "tenant";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenant = request.getHeader(RequestHeaderConstants.TENANT);
        if (tenant != null) {

            BaseContextHandler.setTenantID(tenant);
            return true;
        }
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (null == map || map.isEmpty()) {
            throw new BusinessException("请求地址不包含租户信息！");
        }

        if (map.get(TENANT_PATH) != null && StringUtils.isNotBlank(String.valueOf(map.get(TENANT_PATH)))) {
            BaseContextHandler.setTenantID(String.valueOf(map.get(TENANT_PATH)));
        } else {
            throw new BusinessException("请求地址不包含租户信息！");
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
    }


}
