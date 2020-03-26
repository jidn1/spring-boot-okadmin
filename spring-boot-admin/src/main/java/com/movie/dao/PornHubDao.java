package com.movie.dao;

import com.mvc.base.dao.BaseDao;
import com.movie.model.PornHub;

import java.util.List;


/**
 * <p> PornHubDao </p>
 * @author: jidn
 * @Date :  2019-12-30 17:05:17
 */
public interface PornHubDao extends  BaseDao<PornHub, Integer> {


    List<String> findPornAllType();
}
