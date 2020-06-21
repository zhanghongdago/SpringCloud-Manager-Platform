/*
 *
 *  *  Copyright (C) 2018  Laogandie<2014314038@qq.com>
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

package com.github.wxiaoqi.cloud.auth.module.wf.demo;

import com.github.wxiaoqi.cloud.auth.module.wf.activiti.entity.LeaveDTO;
import com.github.wxiaoqi.cloud.auth.module.wf.service.FlowService;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import com.github.wxiaoqi.cloud.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 老干爹
 * @create 2018/4/5.
 */
@RequestMapping("/admin/wf/leave")
@RestController
@Api(description = "请假任务模块", tags = "请假任务模块")
public class LeaveRest {
    @Autowired
    private FlowService flowService;

    @PostMapping("/apply")
    @ApiOperation(value = "发起请假流程")
    public ObjectRestResponse applyLeave(@RequestBody LeaveDTO leaveDTO) {
        ExecutionEntity pi = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // 创建流程发起人
            map.put("employee", BaseContextHandler.getUsername());
            // 写入流程相关数据
            map.put("reason", leaveDTO.getReason());
            map.put("startDate", new DateTime(leaveDTO.getStartDate()).toDate());
            map.put("endDate", new DateTime(leaveDTO.getEndDate()).toDate());
            // 请假流程key
            String processName = "leave_process";
            pi = flowService.createProcess(processName, map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pi != null) {
            return new ObjectRestResponse().data(pi.getProcessInstanceId());
        } else {
            return new ObjectRestResponse().data("");
        }
    }

    @PostMapping("/reapply")
    @ApiOperation(value = "修改并重新发起请假流程")
    public ObjectRestResponse fixAndApplyLeave(@RequestBody LeaveDTO leaveDTO) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 创建流程发起人
        map.put("reason", leaveDTO.getReason());
        map.put("startDate", new DateTime(leaveDTO.getStartDate()).toDate());
        map.put("endDate", new DateTime(leaveDTO.getEndDate()).toDate());
        List<String> removeVariables = new ArrayList<>();
        removeVariables.add("agree");
        removeVariables.add("suggestion");
        // 请假流程key
        if (flowService.updateProcess(leaveDTO.getId(), map, removeVariables)) {
            return new ObjectRestResponse().data(true);
        }
        return new ObjectRestResponse().data(false);
    }

    @PostMapping("/approve")
    @ApiOperation(value = "审批请假流程")
    public ObjectRestResponse applyLeave(String id, boolean agree, String suggestion) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("agree", agree);
        map.put("suggestion", suggestion);

        if (flowService.executeProcess(id, map)) {
            return new ObjectRestResponse().data(true);
        }
        return new ObjectRestResponse().data(false);
    }

}
