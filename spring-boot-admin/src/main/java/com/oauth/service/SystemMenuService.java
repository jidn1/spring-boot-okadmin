package com.oauth.service;


import com.common.model.PageResult;
import com.oauth.model.SystemMenu;
import com.oauth.model.SystemUser;
import com.oauth.vo.MenuDataVo;
import com.oauth.vo.MenuTree;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * <p> SystemMenuService </p>
 * @author:         jidn
 * @Date :          2019-12-19 15:35:25
 */
public interface SystemMenuService {

    PageResult findPageBySql(Map<String,String> param);

    public List<MenuDataVo> findListByTree();

    public Set<String> findPermissions(String username);

    public List<MenuDataVo> findUserList(SystemUser userInfo);

    List<MenuTree> tree();
}
