package com.movie.service;


import com.common.model.PageResult;
import java.util.Map;


/**
 * <p> MovieService </p>
 * @author:         jidn
 * @Date :          2019-12-20 16:49:50
 */
public interface MovieService {

    PageResult findPageBySql(Map<String,String> param);

    void crawlerPython();
}
