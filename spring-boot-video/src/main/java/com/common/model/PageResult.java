package com.common.model;

import com.alibaba.fastjson.JSON;
import com.common.utils.Crypto;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModelProperty;
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


    @ApiModelProperty(value = "错误编码")
    private Integer code = 0;

    @ApiModelProperty(value = "返回信息")
    private String msg = "查询成功";

    @ApiModelProperty(value = "返回对象")
    private List data = null;

    @ApiModelProperty(value = "加密对象")
    private String Obj;

    @ApiModelProperty(value = "总条数")
    private Long count;

    @ApiModelProperty(value = "当前页")
    private Integer page;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "每页显示条数")
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
