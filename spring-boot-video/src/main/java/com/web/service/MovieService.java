package com.web.service;


import com.web.model.Movie;
import com.common.model.PageResult;
import java.util.Map;

/**
 * <p> MovieService </p>
 * @author:         jidn
 * @Date :          2020-01-02 13:40:48 
 */
public interface MovieService {

   PageResult findPageBySql(Map<String,String> param);
}
