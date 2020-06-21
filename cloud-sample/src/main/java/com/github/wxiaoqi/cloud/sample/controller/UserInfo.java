package com.github.wxiaoqi.cloud.sample.controller;

import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import com.github.wxiaoqi.cloud.sample.feign.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laogandie
 * @create 2019/12/24.
 */
@RestController
@RequestMapping("/sample/")
public class UserInfo {
    @Autowired
    IUserService userService;

    @GetMapping("info")
    public String whoAmI(){
        return String.valueOf(userService.getPermissionByUsername());
    }
}
