package com.github.wxiaoqi.cloud.auth.module.wf.activiti.entity;

import lombok.Data;

/**
 * @author ace
 * @create 2019/3/30.
 */
@Data
public class LeaveDTO {
    String startDate;
    String endDate;
    String reason;
    String id;
}
