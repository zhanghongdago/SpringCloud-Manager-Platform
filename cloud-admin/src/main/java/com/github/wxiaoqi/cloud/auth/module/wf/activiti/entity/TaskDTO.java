package com.github.wxiaoqi.cloud.auth.module.wf.activiti.entity;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ace
 * @create 2019/3/31.
 */
@Data
public class TaskDTO {
    private String processInstanceId;
    private String name;
    private Date createTime;
    private String taskDefinitionKey;
    private String id;
    private String processDefinitionId;
    private String modelName;
    private Map<String,Object> values = new HashMap<>();
}
