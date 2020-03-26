package com.common.handler;

import com.common.model.JsonResult;
import com.exception.CusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/1/15 10:06
 * @Description: 统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CusException.class);

    @ExceptionHandler(value = CusException.class)
    @ResponseBody
    public JsonResult businessException(Exception e){
        String msg = "请求错误";
        if (e instanceof CusException){
            msg = ((CusException) e).getMsg();
        }
        logger.error("find exception:e={}",e.getMessage());
        e.printStackTrace();
        return new JsonResult().setMsg(msg);
    }
}
