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

package com.github.wxiaoqi.cloud.auth.module.dict.service;

import com.github.wxiaoqi.cloud.auth.module.dict.biz.DictValueBiz;
import com.github.wxiaoqi.cloud.auth.module.dict.entity.DictValue;
import com.github.wxiaoqi.cloud.auth.module.dict.biz.DictValueBiz;
import com.github.wxiaoqi.cloud.auth.module.dict.entity.DictValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 老干爹
 * @create 2018/4/2.
 */
@Service
public class DictService {

    @Autowired
    private DictValueBiz dictValueBiz;

    public Map<String,String> getDictValues(String code){
        Example example = new Example(DictValue.class);
        example.createCriteria().andLike("code",code+"%");
        List<DictValue> dictValues = this.dictValueBiz.selectByExample(example);
        Map<String, String> result = dictValues.stream().collect(
                Collectors.toMap(DictValue::getValue, DictValue::getLabelDefault));
        return result;
    }
}
