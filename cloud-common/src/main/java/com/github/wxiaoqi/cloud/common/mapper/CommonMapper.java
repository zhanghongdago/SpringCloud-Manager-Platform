package com.github.wxiaoqi.cloud.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;

/**
 * @author 老干爹
 * @create 2018/2/7.
 */
public interface CommonMapper<T> extends SelectByIdsMapper<T>,Mapper<T> {
}
