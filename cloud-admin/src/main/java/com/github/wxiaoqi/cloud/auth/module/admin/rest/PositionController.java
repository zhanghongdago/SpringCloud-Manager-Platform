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

import com.github.wxiaoqi.cloud.auth.module.admin.biz.PositionBiz;
import com.github.wxiaoqi.cloud.auth.module.admin.entity.Position;
import com.github.wxiaoqi.cloud.auth.module.admin.entity.User;
import com.github.wxiaoqi.cloud.auth.module.admin.vo.DepartTree;
import com.github.wxiaoqi.cloud.auth.module.admin.vo.GroupTree;
import com.github.wxiaoqi.cloud.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.cloud.common.rest.BaseController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/{tenant}/position")
public class PositionController extends BaseController<PositionBiz, Position,String> {
    @RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable String id, String users) {
        baseBiz.modifyPositionUsers(id, users);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<User>> getUsers(@PathVariable("id") String positionId) {
        return new ObjectRestResponse<List<User>>().data(baseBiz.getPositionUsers(positionId));
    }

    @RequestMapping(value = "/{id}/group", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyRoles(@PathVariable String id, String groups) {
        baseBiz.modifyPositionGroups(id, groups);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/group", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<GroupTree>> getRoles(@PathVariable("id") String positionId) {
        return new ObjectRestResponse<List<GroupTree>>().data(baseBiz.getPositionGroups(positionId));
    }

    @RequestMapping(value = "/{id}/depart", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyDeparts(@PathVariable String id, String departs) {
        baseBiz.modifyPositionDeparts(id, departs);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/depart", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<DepartTree>> getDeparts(@PathVariable("id") String positionId) {
        return new ObjectRestResponse<List<DepartTree>>().data(baseBiz.getPositionDeparts(positionId));
    }
}