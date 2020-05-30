package com.common.model;

import com.common.utils.Crypto;
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

    private Boolean success = false;

    private String msg = "";

    private String obj = null;

    private Object data = null;

    private String code = "";

    private String version = "7.0";

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

    public String getObj() {
        return obj;
    }

    public JsonResult setObj(String object) {
        String s = Crypto.aesEncrypt(object);
        this.obj = s;
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
