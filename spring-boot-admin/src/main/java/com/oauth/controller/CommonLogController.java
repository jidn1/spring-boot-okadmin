package com.oauth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.springframework.web.bind.annotation.*;
import com.oauth.model.CommonLog;
import com.oauth.service.CommonLogService;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      jidn
 * @version:     V1.0
 * @Date:        2019-12-26 14:45:30
 */
@Api(value = "")
@RestController
@RequestMapping("/commonLog")
public class CommonLogController{

	@Resource
	private CommonLogService commonLogService;

	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String,String> params = HttpServletRequestUtils.getParams(request);
		return commonLogService.findPageBySql(params);
	}


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(CommonLog commonLog) {
        Criteria<CommonLog,Integer> criteria = new Criteria<>(CommonLog.class);
        criteria.save(commonLog);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

	@RequestMapping("/modify")
	@ResponseBody
	public JsonResult modify(HttpServletRequest request,CommonLog commonLog) {
		Criteria<CommonLog,Integer> criteria = new Criteria<>(CommonLog.class);
		criteria.update(commonLog);
		return new JsonResult().setSuccess(true).setMsg("success");
	}


	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(String idsStr) {
		Criteria<CommonLog,Integer> criteria = new Criteria<>(CommonLog.class);
        String[] idss = idsStr.split(",");
        for(String id : idss){
			criteria.deleteByPrimary(Integer.valueOf(id));
        }
		return new JsonResult().setSuccess(true).setMsg("success");
	}

}
