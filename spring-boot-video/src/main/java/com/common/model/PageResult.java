package com.common.model;

import com.alibaba.fastjson.JSON;
import com.common.utils.Crypto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/19 11:28
 * @Description:
 */
@Data
public class PageResult implements Serializable {


    private Integer code = 0;

    private String msg = "查询成功";

    private List data = null;

    private String Obj;

    private Long count;

    private Integer page;

    private Integer pageSize;

    private boolean success = false;

    public PageResult(List list, Long total, int page, int pageSize){
        //----------------------分页查询底部外壳------------------------------
        //设置分页数据
        String s = Crypto.aesEncrypt(JSON.toJSONString(list));
        this.setObj(s);
       // this.setData(list);
        //设置总记录数
        this.setCount(total);
        // 设置总记录数
        this.setPage(page);
        this.setPageSize(pageSize);
        this.setSuccess(true);
        //----------------------分页查询底部外壳------------------------------

    }


    public PageResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }



    public PageResult setData(List list) {
        this.data = list;
        return this;
    }


    public PageResult setCode(Integer code) {
        this.code = code;
        return this;
    }


    public PageResult setCount(long count) {
        this.count = count;
        return this;
    }

    public PageResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }


}
