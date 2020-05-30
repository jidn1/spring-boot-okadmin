package com.oauth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.common.model.PageResult;
import com.db.Criteria;
import com.github.pagehelper.Page;
import com.oauth.dao.SystemMenuDao;
import com.oauth.enums.ConfigTypeKey;
import com.oauth.model.SystemMenu;
import com.oauth.model.SystemRoleMenu;
import com.oauth.model.SystemUser;
import com.oauth.model.SystemUserRole;
import com.oauth.service.SystemMenuService;

import javax.annotation.Resource;

import com.oauth.vo.MenuDataVo;
import com.oauth.vo.MenuTree;
import com.util.PageFactory;
import com.util.properties.PropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p> SystemMenuService </p>
 *
 * @author: jidn
 * @Date :   2019-12-19 15:35:25
 */
@Service("systemMenuService")
public class SystemMenuServiceImpl implements SystemMenuService {

    @Resource
    private SystemMenuDao systemMenuDao;


    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Criteria<SystemMenu,Integer> criteria = new Criteria<>(SystemMenu.class);
        Page<SystemMenu> page = PageFactory.getPage(param);
        String moviceName = param.get("moviceName");
        String director = param.get("director");
        String mainCharacter = param.get("mainCharacter");
        String mkey = param.get("mkey");

        if(!StringUtils.isEmpty(moviceName)){
            paraMap.put("moviceName","%"+moviceName+"%");
        }
        if(!StringUtils.isEmpty(director)){
            paraMap.put("director","%"+director+"%");
        }
        if(!StringUtils.isEmpty(mainCharacter)){
            paraMap.put("mainCharacter","%"+mainCharacter+"%");
        }
        if(!StringUtils.isEmpty(mkey)){
            paraMap.put("mkey",mkey);
        }

        List<SystemMenu> list = systemMenuDao.findPageBySql(paraMap);
        return new PageResult(list,page.getTotal(), page.getPages(), page.getPageSize());
    }

    @Override
    public List<MenuDataVo> findListByTree(){
        List<MenuDataVo> menus = new ArrayList<>();
        try {
            List<MenuDataVo> roleMenuList = systemMenuDao.findAllRoleMenuList();

            if (roleMenuList != null && roleMenuList.size() > 0) {
                for (MenuDataVo m : roleMenuList) {
                    if (StringUtils.isEmpty(m.getPkey())) {
                        menus.add(m);
                    }
                }

                for (MenuDataVo sm : menus) {
                    List<MenuDataVo> sonMenus = new ArrayList<>();
                    for (MenuDataVo m : roleMenuList) {
                        if (m.getPkey() != null && m.getPkey().equals(sm.getMkey()) ) {
                            sonMenus.add(m);
                        }
                    }
                    sm.setChildren(sonMenus);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return menus;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> set = new HashSet<String>();
        List<MenuDataVo> menus = new ArrayList<>();
        try {
            List<MenuDataVo> roleMenuList = new ArrayList<>();

            Criteria<SystemUser, Integer> userCriteria = new Criteria<>(SystemUser.class);
            userCriteria.addFilter("username=", username);
            SystemUser userInfo = userCriteria.findByExample();


            if(userInfo.getUsername().equals(PropertiesUtils.APP.getProperty("app.username"))){
                roleMenuList = systemMenuDao.findAllRoleMenuList();
            } else {
                Criteria<SystemUserRole, Integer> userRoleCriteria = new Criteria<>(SystemUserRole.class);
                userRoleCriteria.addFilter("userId=", userInfo.getId());
                SystemUserRole userRoles = userRoleCriteria.findByExample();

                roleMenuList = systemMenuDao.findRoleMenuList(userRoles.getRoleId());
            }

            if (roleMenuList != null && roleMenuList.size() > 0) {
                for (MenuDataVo m : roleMenuList) {
                    if (StringUtils.isEmpty(m.getPkey())) {
                        menus.add(m);
                    }
                }

                for (MenuDataVo sm : menus) {
                    List<MenuDataVo> sonMenus = new ArrayList<>();
                    for (MenuDataVo m : roleMenuList) {
                        if (m.getPkey() != null && m.getPkey().equals(sm.getMkey()) && m.getResourceType().equals(ConfigTypeKey.MENU.getKey())) {
                            sonMenus.add(m);
                        }
                    }
                    sm.setChildren(sonMenus);
                }
            }


            for(MenuDataVo m : menus){
                set.add(m.getHref());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    @Override
    public List<MenuDataVo> findUserList(SystemUser userInfo) {
        List<MenuDataVo> menus = new ArrayList<>();
        try {
            List<MenuDataVo> roleMenuList = new ArrayList<>();

            if(userInfo.getUsername().equals(PropertiesUtils.APP.getProperty("app.username"))){
                roleMenuList = systemMenuDao.findAllRoleMenuList();
            } else {
                Criteria<SystemUserRole, Integer> userRoleCriteria = new Criteria<>(SystemUserRole.class);
                userRoleCriteria.addFilter("userId=", userInfo.getId());
                SystemUserRole userRoles = userRoleCriteria.findByExample();

                roleMenuList = systemMenuDao.findRoleMenuList(userRoles.getRoleId());
            }

            if (roleMenuList != null && roleMenuList.size() > 0) {
                for (MenuDataVo m : roleMenuList) {
                    if (StringUtils.isEmpty(m.getPkey())) {
                        menus.add(m);
                    }
                }

                for (MenuDataVo sm : menus) {
                    List<MenuDataVo> sonMenus = new ArrayList<>();
                    for (MenuDataVo m : roleMenuList) {
                        if (m.getPkey() != null && m.getPkey().equals(sm.getMkey())) {
                            sonMenus.add(m);
                        }
                    }
                    sm.setChildren(sonMenus);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return menus;
    }


    @Override
    public List<MenuTree> tree(){
        List<MenuTree> tree = new ArrayList<>();
        List<SystemMenu> allOne = systemMenuDao.findAllOne();
        allOne.forEach(SystemMenu::getName);
        if(allOne != null && allOne.size() > 0){
            for(SystemMenu s : allOne){
                MenuTree t = new MenuTree();
                t.setTitle(s.getName());
                t.setSpread(false);
                t.setMkey(s.getMkey());
                List<MenuTree> children = systemMenuDao.findAllByPkey(s.getMkey());
                t.setChildren(children);
                tree.add(t);
            }
        }

        return tree;
    }
}
