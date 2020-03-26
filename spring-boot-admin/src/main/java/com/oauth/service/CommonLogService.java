package com.oauth.service;


import com.oauth.model.CommonLog;
import com.common.model.PageResult;
import java.util.Map;

/**
 * <p> CommonLogService </p>
 * @author:         jidn
 * @Date :          2019-12-26 14:45:30 
 */
public interface CommonLogService {

   PageResult findPageBySql(Map<String,String> param);
}
