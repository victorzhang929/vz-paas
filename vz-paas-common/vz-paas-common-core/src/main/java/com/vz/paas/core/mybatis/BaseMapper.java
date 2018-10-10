package com.vz.paas.core.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 基础映射
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 20:40:32
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
