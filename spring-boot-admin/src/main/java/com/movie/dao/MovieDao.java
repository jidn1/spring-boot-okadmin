package com.movie.dao;

import com.mvc.base.dao.BaseDao;
import com.movie.model.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * <p> MovieDao </p>
 * @author: jidn
 * @Date :  2019-12-20 16:49:50 
 */
public interface MovieDao extends BaseDao<Movie, Long> {

    /**
     * 分页查询
     * @param paraMap
     * @return
     */

    List<Movie> findPageBySql(Map<String,Object> paraMap);
}
