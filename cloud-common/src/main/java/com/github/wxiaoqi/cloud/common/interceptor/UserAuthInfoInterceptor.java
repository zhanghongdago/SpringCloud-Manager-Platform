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

package com.github.wxiaoqi.cloud.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.cloud.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import com.github.wxiaoqi.cloud.common.exception.auth.NonLoginException;
import com.github.wxiaoqi.cloud.common.msg.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 老干爹
 * @version 2017/9/10
 */
@Slf4j
public class UserAuthInfoInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Object principal = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            if (principal == null) {
                throw new NonLoginException("登录过期");
            }
            Jwt jwt = (Jwt) principal;
            BaseContextHandler.setToken(RequestHeaderConstants.JWT_TOKEN_TYPE + jwt.getTokenValue());
            BaseContextHandler.setUsername(String.valueOf(jwt.getClaims().get("user_name")));
            BaseContextHandler.setName(String.valueOf(jwt.getClaims().get("userName")));
            BaseContextHandler.setUserID(String.valueOf(jwt.getClaims().get("userId")));
            BaseContextHandler.setDepartID(String.valueOf(jwt.getClaims().get("depart")));
            if(BaseContextHandler.getTenantID()==null){
                BaseContextHandler.setTenantID(String.valueOf(jwt.getClaims().get("tenant")));
            }
            return super.preHandle(request, response, handler);
        } catch (NonLoginException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("UTF-8");
            response.getOutputStream().println(JSON.toJSONString(new BaseResponse(ex.getStatus(), ex.getMessage())));
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }


}
