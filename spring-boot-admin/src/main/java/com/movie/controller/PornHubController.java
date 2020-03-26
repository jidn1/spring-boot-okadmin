package com.movie.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.annotation.CommonLog;
import com.common.constants.ConstantsRedisKey;
import com.movie.dao.PornHubDao;
import com.movie.vo.SourceJson;
import com.redis.BaseRedis;
import com.thread.ThreadPool;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import com.util.file.FileUtil;
import com.util.uuid.UUIDUtil;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import com.movie.model.PornHub;
import com.movie.service.PornHubService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      jidn
 * @version:     V1.0
 * @Date:        2019-12-30 17:05:17
 */
@Api(value = "")
@RestController
@RequestMapping("/pornHub")
public class PornHubController{

	@Resource
	private PornHubService pornHubService;
	@Resource
	private PornHubDao pornHubDao;

	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
		return pornHubService.findPageBySql(params);
	}


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(PornHub pornHub) {
        Criteria<PornHub,Integer> criteria = new Criteria<>(PornHub.class);
        criteria.save(pornHub);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

	@RequestMapping("/modify")
	@ResponseBody
	public JsonResult modify(HttpServletRequest request,PornHub pornHub) {
		Criteria<PornHub,Integer> criteria = new Criteria<>(PornHub.class);
		criteria.update(pornHub);
		return new JsonResult().setSuccess(true).setMsg("success");
	}


	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(String idsStr) {
		Criteria<PornHub,Integer> criteria = new Criteria<>(PornHub.class);
        String[] idss = idsStr.split(",");
        for(String id : idss){
			criteria.deleteByPrimary(Integer.valueOf(id));
        }
		return new JsonResult().setSuccess(true).setMsg("success");
	}

	@CommonLog(name = "导入json资源")
	@RequestMapping(value = "/importJson", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult importJson(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
		JsonResult json = new JsonResult();
		try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			if (multipartRequest != null) {
                Reader reader = new InputStreamReader(file.getInputStream(),"utf-8");
				int ch = 0;
				StringBuffer sb = new StringBuffer();
				while ((ch = reader.read()) != -1) {
					sb.append((char) ch);
				}
				reader.close();
				String jsonStr = sb.toString();
				jedis.set(ConstantsRedisKey.CRAW_URL,jsonStr);
				System.out.println("导入json资源完成");
            }
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@CommonLog(name = "手动爬取资源")
	@RequestMapping("/manualCraw")
	@ResponseBody
	public JsonResult manualCraw(String idsStr) {
		ThreadPool.exe(new Runnable() {
			@Override
			public void run() {
				try {
					pornHubService.crawlerPython();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return new JsonResult().setSuccess(true).setMsg("爬取中,请稍后,大概需要几分钟");
	}

	@RequestMapping("/refreshRedis")
	@ResponseBody
	public JsonResult refreshRedis() {
		ThreadPool.exe(new Runnable() {
			@Override
			public void run() {
                Criteria<PornHub, Integer> criteria = new Criteria<>(PornHub.class);
				try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()){

                    List<String> allType = pornHubDao.findPornAllType();
                    jedis.set(ConstantsRedisKey.VIDEO_PORN_TYPE,JSONObject.toJSONString(allType));

					jedis.del(ConstantsRedisKey.VIDEO_PORN_LIST);
                    List<PornHub> list = criteria.findAll();
                    for (PornHub p : list) {
                        jedis.rpush(ConstantsRedisKey.VIDEO_PORN_LIST, JSONObject.toJSONString(p));
                    }

                    for(String type : allType){
                        Criteria<PornHub, Integer> criteria1 = new Criteria<>(PornHub.class);
                        criteria1.addFilter("type=",type);
                        List<PornHub> listByExample = criteria1.findListByExample();
						jedis.del(ConstantsRedisKey.VIDEO_PORN_LIST+":"+type);
                        for (PornHub p : listByExample) {
                            jedis.rpush(ConstantsRedisKey.VIDEO_PORN_LIST+":"+type, JSONObject.toJSONString(p));
                        }
                    }

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return new JsonResult().setSuccess(true).setMsg("success");
	}

}
