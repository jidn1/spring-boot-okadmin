package com.oauth.service;


import com.oauth.model.Config;
import com.common.model.PageResult;
import java.util.Map;

/**
 * <p> ConfigService </p>
 * @author:         jidn
 * @Date :          2019-12-27 18:14:58
 */
public interface ConfigService {

   /**
    * 分页
    * @param param
    * @return
    */
   PageResult findPageBySql(Map<String,String> param);

   /**
    * 初始化基础配置
    */
   public void initRedisConfig();
}
