package com.github.wxiaoqi.cloud.gate.feign;

import com.github.wxiaoqi.cloud.dto.vo.authority.PermissionInfo;
import com.github.wxiaoqi.cloud.gate.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * ${DESCRIPTION}
 *
 * @author laogandie
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "cloud-admin", fallback = UserServiceFallback.class)
public interface IUserService {
    @RequestMapping(value = "/api/user/permissions", method = RequestMethod.GET)
    List<PermissionInfo> getPermissionByUsername();

    @RequestMapping(value = "/api/permissions", method = RequestMethod.GET)
    List<PermissionInfo> getAllPermissionInfo();
}
