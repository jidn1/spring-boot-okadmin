package com.oauth.dao;

import com.mvc.base.dao.BaseDao;
import com.oauth.model.SystemMenu;
import com.oauth.vo.MenuDataVo;

import java.util.List;


/**
 * <p> SystemMenuDao </p>
 * @author: jidn
 * @Date :  2019-12-19 15:35:25
 */
public interface SystemMenuDao extends  BaseDao<SystemMenu, Integer> {

    /**
     * 查询权限菜单
     * @param roleId
     * @return
     */
    public List<MenuDataVo> findRoleMenuList(Integer roleId);


    public List<MenuDataVo> findAllRoleMenuList();
}
