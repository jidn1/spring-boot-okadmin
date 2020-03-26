package com.movie.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.annotation.CommonLog;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import com.common.utils.PasswordHelper;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.springframework.web.bind.annotation.*;
import com.movie.model.PersonInfo;
import com.movie.service.PersonInfoService;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      jidn
 * @version:     V1.0
 * @Date:        2019-12-25 16:09:31
 */
@Api(value = "")
@RestController
@RequestMapping("/personInfo")
public class PersonInfoController{

	@Resource
	private PersonInfoService personInfoService;

	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
		return personInfoService.findPageBySql(params);
	}


    @RequestMapping("/save")
    @ResponseBody
	@CommonLog(name = "添加用户")
    public JsonResult save(PersonInfo personInfo) {
        Criteria<PersonInfo,Integer> criteria = new Criteria<>(PersonInfo.class);
		PasswordHelper passwordHelper = new PasswordHelper();
        String s = passwordHelper.encryString(personInfo.getPassword(), personInfo.getSalt());
        personInfo.setPassword(s);
        criteria.save(personInfo);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

	@RequestMapping("/modify")
	@ResponseBody
	@CommonLog(name = "修改用户")
	public JsonResult modify(HttpServletRequest request,PersonInfo personInfo) {
		Criteria<PersonInfo,Integer> criteria = new Criteria<>(PersonInfo.class);
		criteria.update(personInfo);
		return new JsonResult().setSuccess(true).setMsg("success");
	}

	@CommonLog(name = "移除用户")
	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(String idsStr) {
		Criteria<PersonInfo,Integer> criteria = new Criteria<>(PersonInfo.class);
        String[] idss = idsStr.split(",");
        for(String id : idss){
			criteria.deleteByPrimary(Integer.valueOf(id));
        }
		return new JsonResult().setSuccess(true).setMsg("success");
	}

}
