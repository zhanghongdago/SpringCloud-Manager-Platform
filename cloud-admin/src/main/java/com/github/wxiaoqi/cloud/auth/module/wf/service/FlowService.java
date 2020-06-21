package com.github.wxiaoqi.cloud.auth.module.wf.service;

import com.github.wxiaoqi.cloud.auth.module.admin.biz.PositionBiz;
import com.github.wxiaoqi.cloud.auth.module.admin.biz.PositionBiz;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FlowService {
    @Autowired
    private PositionBiz positionBiz;

    @Autowired
    TaskService taskService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    /**
     * 重新修改发起流程
     *
     * @param taskId
     * @param map
     * @param removeVariables
     * @return
     */
    public boolean updateProcess(String taskId, Map<String, Object> map, List<String> removeVariables) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (BaseContextHandler.getUsername().equals(task.getAssignee())) {
            task.setAssignee(BaseContextHandler.getUsername());
            taskService.saveTask(task);
            taskService.complete(task.getId(), map);
            runtimeService.removeVariables(task.getExecutionId(), removeVariables);
            return true;
        }
        return false;
    }

    public boolean isAuditor(String taskId) {
        // 获取当前人拥有的流程岗位
        List<String> userFlowPositions = positionBiz.getPositionIdsByUserId(BaseContextHandler.getUserID());
        // 获取当前流程具有审批的岗位
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        boolean flag = false;
        // 判断当前人是否需要相关审批岗位
        for (IdentityLink link : identityLinksForTask) {
            long count = userFlowPositions.stream().filter(positionName -> positionName.equals(link.getGroupId())).count();
            if (count > 0) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public boolean executeProcess(String taskId, Map<String, Object> map) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 当前人具有相关审批权限,则可以审批请假流程
        if (isAuditor(task.getId())) {
            task.setAssignee(BaseContextHandler.getUsername());
            taskService.saveTask(task);
            taskService.complete(task.getId(), map);
            return true;
        }
        return false;
    }

    public ExecutionEntity createProcess(String processName, Map<String, Object> map) {
        ExecutionEntity pi = null;
        //流程启动
        identityService.setAuthenticatedUserId(BaseContextHandler.getUsername());
        // 启动一个流程实例
        pi = (ExecutionEntity) runtimeService.startProcessInstanceByKey(processName, map);
        String taskId = pi.getTasks().get(0).getId();
        // 根据实例把流程往下推
        taskService.complete(taskId, map);
        return pi;
    }


}
