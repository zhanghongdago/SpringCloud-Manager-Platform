/*
 *  Copyright (C) 2018  老干爹<2014314038@qq.com>

 *  Boot-Platform 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老干爹传播人，奖励1000。
 *  老干爹将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.wxiaoqi.cloud.common.exception.base;

import com.github.wxiaoqi.cloud.common.constant.RestCodeConstants;
import com.github.wxiaoqi.cloud.common.exception.BaseException;

/**
 * 业务异常基础类
 * @author 老干爹
 * @version 2018/1/13.
 */
public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(message, RestCodeConstants.EX_BUSINESS_BASE_CODE);
    }
}
