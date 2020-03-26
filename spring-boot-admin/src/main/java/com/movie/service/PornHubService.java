package com.movie.service;


import com.movie.model.PornHub;
import com.common.model.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p> PornHubService </p>
 * @author:         jidn
 * @Date :          2019-12-30 17:05:17
 */
public interface PornHubService {

   PageResult findPageBySql(Map<String,String> param);

   void crawlerPorn();

   void crawlerPython();



}
