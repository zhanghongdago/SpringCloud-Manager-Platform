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

package com.github.wxiaoqi.cloud.auth.module.admin.rest;

import com.github.wxiaoqi.cloud.auth.module.admin.service.PermissionService;
import com.github.wxiaoqi.cloud.auth.module.admin.biz.MenuBiz;
import com.github.wxiaoqi.cloud.auth.module.admin.biz.UserBiz;
import com.github.wxiaoqi.cloud.auth.module.admin.entity.Menu;
import com.github.wxiaoqi.cloud.auth.module.admin.entity.User;
import com.github.wxiaoqi.cloud.auth.module.admin.service.PermissionService;
import com.github.wxiaoqi.cloud.auth.module.admin.vo.AuthUser;
import com.github.wxiaoqi.cloud.auth.module.admin.vo.FrontUser;
import com.github.wxiaoqi.cloud.auth.module.admin.vo.MenuTree;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import com.github.wxiaoqi.cloud.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.cloud.common.rest.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 老干爹
 * @version 2017-06-08 11:51
 */
@RestController
@RequestMapping("/admin/{tenant}/user")
public class UserController extends BaseController<UserBiz, User, String> {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuBiz menuBiz;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ObjectRestResponse<AuthUser> validate(String username) {
        AuthUser user = new AuthUser();
        BeanUtils.copyProperties(baseBiz.getUserByUsername(username), user);
        return new ObjectRestResponse<AuthUser>().data(user);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ObjectRestResponse<Boolean> changePassword(String oldPass, String newPass) {
        return new ObjectRestResponse<Boolean>().data(baseBiz.changePassword(oldPass, newPass));
    }


    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserInfo() throws Exception {
        FrontUser userInfo = permissionService.getUserInfo();
        if (userInfo == null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
    public @ResponseBody
    List<MenuTree> getMenusByUsername() throws Exception {
        return permissionService.getMenusByUsername();
    }

    @RequestMapping(value = "/front/menu/all", method = RequestMethod.GET)
    public @ResponseBody
    List<Menu> getAllMenus() throws Exception {
        return menuBiz.selectListAll();
    }

    @RequestMapping(value = "/dataDepart", method = RequestMethod.GET)
    public List<String> getUserDataDepartIds(String userId) {
        if (BaseContextHandler.getUserID().equals(userId)) {
            return baseBiz.getUserDataDepartIds(userId);
        }
        return new ArrayList<>();
    }

    @RequestMapping("/")
    public Principal userInfo(Principal principal) {
        return principal;
    }

}
