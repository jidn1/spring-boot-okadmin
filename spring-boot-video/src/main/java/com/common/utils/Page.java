package com.common.utils;

import lombok.Data;

import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2020/1/2 17:31
 * @Description:
 */
@Data
public class Page {

    private Long total;

    private Integer pages;

    private Integer pageSize;

    public Page(Map<String,String> map){

        Integer offset =  Integer.valueOf(map.get("page"));
        Integer limit =  Integer.valueOf(map.get("limit"));

        if(offset == 1){
            pages = 0;
            pageSize = (limit * offset) - 1;
        } else {
            pages = ((offset -1) * limit);
            pageSize = (limit * offset) - 1;
        }
    }

}
