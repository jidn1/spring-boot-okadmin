package com.movie.service.impl;

import com.movie.model.PersonInfo;
import com.movie.service.PersonInfoService;
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
 * <p> PersonInfoService </p>
 * @author:  jidn
 * @Date :   2019-12-25 16:09:31 
 */
@Service("personInfoService")
public class PersonInfoServiceImpl implements PersonInfoService{
	

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Criteria<PersonInfo,Integer> criteria = new Criteria<>(PersonInfo.class);
        Page<PersonInfo> page = PageFactory.getPage(param);
        criteria.findAll();
        return new PageResult(page,page.getTotal(), page.getPages(), page.getPageSize());
    }

}
