package com.newbig.codetemplate.dal;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 通用 Mapper, 如果被扫描到会报异常
 */
public interface MyMapper<T> extends Mapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {
}
