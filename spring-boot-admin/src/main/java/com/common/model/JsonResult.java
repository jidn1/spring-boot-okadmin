package com.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/19 11:28
 * @Description:
 */
@Data
public class JsonResult implements Serializable {

    @ApiModelProperty(value = "是否成功")
    private Boolean success = false;

    @ApiModelProperty(value = "返回信息")
    private String msg = "";

    @ApiModelProperty(value = "返回对象")
    private Object obj = null;

    @ApiModelProperty(value = "返回对象")
    private Object data = null;

    @ApiModelProperty(value = "错误编码")
    private String code = "";

    @ApiModelProperty(value = "版本号")
    private String version = "7.0";

    @ApiModelProperty(value = "版本号")
    private Integer count;

    public Boolean getSuccess() {
        return success;
    }

    public JsonResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public JsonResult setObj(Object object) {
        this.obj = object;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonResult setData(Object object) {
        this.data = object;
        return this;
    }

    /**
     * <p> TODO</p>
     *
     * @return: String
     */
    public String getCode() {
        return code;
    }

    /**
     * <p> TODO</p>
     *
     * @return: String
     */
    public JsonResult setCode(String code) {
        this.code = code;
        return this;
    }


    public String getVersion() {
        return version;
    }

    public JsonResult setVersion(String version) {
        this.version = version;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public JsonResult setCount(Integer count) {
        this.count = count;
        return this;
    }
}
