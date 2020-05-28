package com.oauth.controller;

import com.common.model.JsonResult;
import com.common.model.PageResult;
import com.db.Criteria;
import com.oauth.model.SystemMenu;
import com.oauth.service.SystemMenuService;
import com.oauth.vo.MenuTree;
import com.util.HttpServletRequestUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      jidn
 * @version:     V1.0
 * @Date:        2020-01-07 16:51:36
 */
@Api(value = "")
@RestController
@RequestMapping("/systemMenu")
public class SystemMenuController {

	@Resource
	private SystemMenuService systemMenuService;

	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
		return systemMenuService.findPageBySql(params);
	}


	@RequestMapping("/tree")
	@ResponseBody
	public JsonResult tree(HttpServletRequest request) {
        List<MenuTree> tree = systemMenuService.tree();
        return new JsonResult().setSuccess(true).setData(tree);
	}

    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(SystemMenu systemMenu) {
        Criteria<SystemMenu,Integer> criteria = new Criteria<>(SystemMenu.class);
        criteria.save(systemMenu);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

	@RequestMapping("/modify")
	@ResponseBody
	public JsonResult modify(HttpServletRequest request,SystemMenu systemMenu) {
		Criteria<SystemMenu,Integer> criteria = new Criteria<>(SystemMenu.class);
		criteria.update(systemMenu);
		return new JsonResult().setSuccess(true).setMsg("success");
	}


	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(String idsStr) {
		Criteria<SystemMenu,Integer> criteria = new Criteria<>(SystemMenu.class);
        String[] idss = idsStr.split(",");
        for(String id : idss){
			criteria.deleteByPrimary(Integer.valueOf(id));
        }
		return new JsonResult().setSuccess(true).setMsg("success");
	}

}
