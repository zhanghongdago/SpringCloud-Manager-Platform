/*
 *
 *  *  Copyright (C) 2018  laogandie<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
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

package com.github.wxiaoqi.cloud.sample.mapper;


import com.github.wxiaoqi.cloud.common.data.Tenant;
import com.github.wxiaoqi.cloud.common.mapper.CommonMapper;
import com.github.wxiaoqi.cloud.sample.entity.SampleDataTest;

/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-11 16:05:19
 */
//@Depart // 该注解表示开启部门数据权限隔离
@Tenant // 该注解表示开启租户数据隔离
public interface SampleDataTestMapper extends CommonMapper<SampleDataTest> {
	
}
