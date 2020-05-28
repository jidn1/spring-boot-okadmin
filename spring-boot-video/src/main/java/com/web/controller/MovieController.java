package com.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.common.aop.ServiceLimit;
import com.common.utils.HttpServletRequestUtils;
import com.common.model.PageResult;
import org.springframework.web.bind.annotation.*;
import com.web.service.MovieService;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      jidn
 * @version:     V1.0
 * @Date:        2020-01-02 13:40:48
 */
@RestController
@RequestMapping("/movie")
public class MovieController{

	@Resource
	private MovieService movieService;

	@ServiceLimit(limitType= ServiceLimit.LimitType.IP)
	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
		return movieService.findPageBySql(params);
	}



}
