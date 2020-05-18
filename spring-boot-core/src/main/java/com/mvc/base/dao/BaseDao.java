package com.mvc.base.dao;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;
import com.auth.AuthCheckUtil;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 12:52
 * @Description: 特别注意，该接口不能被扫描到，否则会出错
 */
public interface BaseDao<T,PK> extends BaseMapper<T>, Mapper<T> {
}
