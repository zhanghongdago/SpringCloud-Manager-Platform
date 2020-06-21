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

package com.github.wxiaoqi.cloud.auth.module.admin.mapper;

import com.github.wxiaoqi.cloud.auth.module.admin.entity.Depart;
import com.github.wxiaoqi.cloud.auth.module.admin.entity.Group;
import com.github.wxiaoqi.cloud.auth.module.admin.entity.Position;
import com.github.wxiaoqi.cloud.auth.module.admin.entity.User;
import com.github.wxiaoqi.cloud.common.data.Tenant;
import com.github.wxiaoqi.cloud.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 老干爹
 * @email 2014314038@qq.com
 * @version 2018-02-04 19:06:43
 */
@Tenant
public interface PositionMapper extends CommonMapper<Position> {
    /**
     * 批量删除岗位中得用户
     * @param positionId
     */
    void deletePositionUsers(String positionId);

    /**
     * 岗位增加用户
     * @param id
     * @param positionId
     * @param userId
     */
    void insertPositionUser(@Param("id") String id, @Param("positionId") String positionId, @Param("userId") String userId, @Param("tenantId") String tenantId);

    /**
     * 获取岗位关联的用户
     * @param positionId
     * @return
     */
    List<User> selectPositionUsers(String positionId);

    /**
     * 删除岗位关联的角色
     * @param positionId
     */
    void deletePositionGroups(String positionId);

    /**
     * 插入岗位关联的角色
     * @param id
     * @param positionId
     * @param groupId
     */
    void insertPositionGroup(@Param("id") String id, @Param("positionId") String positionId, @Param("groupId") String groupId, @Param("tenantId") String tenantId);

    /**
     * 获取岗位关联的角色
     * @param positionId
     * @return
     */
    List<Group> selectPositionGroups(@Param("positionId") String positionId);

    /**
     * 移除岗位下授权的部门
     * @param positionId
     */
    void deletePositionDeparts(String positionId);

    /**
     * 添加岗位下授权的部门
     * @param id
     * @param positionId
     * @param departId
     */
    void insertPositionDepart(@Param("id") String id, @Param("positionId") String positionId, @Param("departId") String departId, @Param("tenantId") String tenantId);

    /**
     * 获取岗位授权的部门
     * @param positionId
     * @return
     */
    List<Depart> selectPositionDeparts(String positionId);


    /**
     * 获取用户的流程岗位
     * @return
     */
    List<Position> selectUserFlowPosition(String userId);
}
