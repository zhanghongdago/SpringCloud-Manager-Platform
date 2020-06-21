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

package com.github.wxiaoqi.cloud.auth.module.admin.biz;

import com.github.wxiaoqi.cloud.auth.module.admin.mapper.TenantMapper;
import com.github.wxiaoqi.cloud.auth.module.admin.mapper.UserMapper;
import com.github.wxiaoqi.cloud.auth.module.admin.entity.Tenant;
import com.github.wxiaoqi.cloud.auth.module.admin.entity.User;
import com.github.wxiaoqi.cloud.auth.module.admin.mapper.TenantMapper;
import com.github.wxiaoqi.cloud.auth.module.admin.mapper.UserMapper;
import com.github.wxiaoqi.cloud.common.biz.BusinessBiz;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import com.github.wxiaoqi.cloud.common.exception.base.BusinessException;
import com.github.wxiaoqi.cloud.common.util.BooleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 租户表
 *
 * @author 老干爹
 * @version 2018-02-08 21:42:09
 * @email 2014314038@qq.com
 */
@Service
public class TenantBiz extends BusinessBiz<TenantMapper, Tenant> {
    @Autowired
    private UserMapper userMapper;

    public void updateUser(String id, String userId) {
        Tenant tenant = this.mapper.selectByPrimaryKey(id);
        tenant.setOwner(userId);
        updateSelectiveById(tenant);
        User user = userMapper.selectByPrimaryKey(userId);
        user.setTenantId(id);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void insertSelective(Tenant entity) {
        validatePermission();
        checkUniqueTenant(entity);
        super.insertSelective(entity);
    }

    private void checkUniqueTenant(Tenant entity) {
        Tenant condition = new Tenant();
        condition.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        condition.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        condition.setIsSuperTenant(BooleanUtil.BOOLEAN_TRUE);
        if(this.selectList(condition).size()>0){
            entity.setIsSuperTenant(BooleanUtil.BOOLEAN_FALSE);
        }
    }

    /**
     * 判断当前用户是否有效
     */
    private void validatePermission() {
        Tenant tenant = this.selectById(BaseContextHandler.getTenantID());
        if (tenant == null || BooleanUtil.BOOLEAN_FALSE.equals(tenant.getIsSuperTenant())) {
            throw new BusinessException("当前用户所在租户并非超级租户，无权操作！");
        }
        User condition = new User();
        condition.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        condition.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        condition.setId(BaseContextHandler.getUserID());
        condition.setIsSuperAdmin(BooleanUtil.BOOLEAN_TRUE);
        User user = userMapper.selectOne(condition);
        if(user==null){
            throw new BusinessException("当前用户并非平台超管，无权操作！");
        }
    }

    @Override
    public void updateById(Tenant entity) {
        validatePermission();
        checkUniqueTenant(entity);
        super.updateById(entity);
    }

    @Override
    public void updateSelectiveById(Tenant entity) {
        validatePermission();
        checkUniqueTenant(entity);
        super.updateSelectiveById(entity);
    }

    @Override
    public void deleteById(Object id) {
        Tenant tenant = this.selectById(id);
        tenant.setIsDeleted(BooleanUtil.BOOLEAN_TRUE);
        updateSelectiveById(tenant);
    }
}