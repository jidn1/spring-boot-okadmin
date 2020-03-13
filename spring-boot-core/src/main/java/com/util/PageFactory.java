package com.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.io.Serializable;
import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/23 11:44
 * @Description:
 */
public class PageFactory<T extends Serializable> {


    public static Page getPage(Map<String,String> map){
        Page page = null;
        Integer offset =  Integer.valueOf(map.get("page"));
        Integer limit =  Integer.valueOf(map.get("limit"));

        if(limit==-1){
            page= PageHelper.startPage(offset/limit+1, 0);
        }else{
            page = PageHelper.startPage(offset, limit);
        }
        return page;
    }
}
