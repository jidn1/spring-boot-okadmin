package com.movie.service;


import com.movie.model.PersonInfo;
import com.common.model.PageResult;
import java.util.Map;

/**
 * <p> PersonInfoService </p>
 * @author:         jidn
 * @Date :          2019-12-25 16:09:31 
 */
public interface PersonInfoService {

   PageResult findPageBySql(Map<String,String> param);
}
