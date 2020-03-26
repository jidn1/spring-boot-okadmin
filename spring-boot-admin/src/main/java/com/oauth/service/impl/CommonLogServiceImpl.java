package com.oauth.service.impl;

import com.oauth.model.CommonLog;
import com.oauth.service.CommonLogService;
import com.db.Criteria;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.common.model.PageResult;
import com.util.PageFactory;
import com.github.pagehelper.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> CommonLogService </p>
 * @author:  jidn
 * @Date :   2019-12-26 14:45:30 
 */
@Service("commonLogService")
public class CommonLogServiceImpl implements CommonLogService{
	

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Criteria<CommonLog,Integer> criteria = new Criteria<>(CommonLog.class);
        Page<CommonLog> page = PageFactory.getPage(param);
        criteria.findAll();
        return new PageResult(page,page.getTotal(), page.getPages(), page.getPageSize());
    }

}
