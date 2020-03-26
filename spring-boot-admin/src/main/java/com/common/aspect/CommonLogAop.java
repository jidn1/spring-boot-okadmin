package com.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annotation.CommonLog;
import com.common.constants.ConstantsRedisKey;
import com.common.utils.IpUtil;
import com.oauth.model.SystemUser;
import com.util.HttpServletRequestUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/26 14:25
 * @Description:
 */
@Aspect
@Component
public class CommonLogAop {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @AfterReturning(value = "@annotation(com.annotation.CommonLog)", argNames = "joinPoint,returnValue", returning = "returnValue")
    public void afterInsertMethod(final JoinPoint joinPoint, final Object returnValue) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Class<? extends Object> clazz = joinPoint.getTarget().getClass();
            String methodName = joinPoint.getSignature().getName();
            Method[] declaredMethods = clazz.getDeclaredMethods();
            Method method = null;
            for (Method m : declaredMethods) {
                if (methodName.equals(m.getName())) {
                    method = m;
                    break;
                }
            }

            CommonLog commonLog = method.getAnnotation(CommonLog.class);
            String name = commonLog.name();

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            //id地址
            String ip = IpUtil.getIpAddrByRequest(request);

            Object target = joinPoint.getTarget();

            String argsJson = "";
            Map<String, String> argMap = new HashMap<String, String>();
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                Object arg = joinPoint.getArgs()[i];
                if (null != arg) {
                    if (arg instanceof HttpServletRequest) {
                        HttpServletRequest argRequest = (HttpServletRequest) arg;
                        argMap = HttpServletRequestUtils.getParams(argRequest);
                    } else {
                        argMap.put("resultParam" + i, arg.toString());
                    }
                }
            }
            argsJson = JSON.toJSONString(argMap);

            SystemUser user =(SystemUser) SecurityUtils.getSubject().getSession().getAttribute(ConstantsRedisKey.SESSION_USER_INFO);
            String userName = user.getUsername();

            String result;
            String remark = "";
            try {
                result = JSON.toJSONString(returnValue);
                if (!StringUtils.isEmpty(request)) {
                    JSONObject jsonObject = JSON.parseObject(result);
                    String tip = "[操作失败]";
                    if (jsonObject.getBoolean("success")) {
                        tip = "[操作成功]";
                    }
                    remark = createRemark(methodName, tip, argMap);
                }
            } catch (Exception e) {
            }


            //保存到日志中
            jdbcTemplate.update("insert into fh_common_log(ip, userName,name, methodName, args, target, created,remark) values ("
                    + "'" + ip + "',"
                    + "'" + userName + "',"
                    + "'" + name + "',"
                    + "'" + methodName + "',"
                    + "'" + argsJson + "',"
                    + "'" + target + "',"
                    + "'" + sdf.format(new Date()) + "' ,"
                    + "'" + remark + "')"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成备注
     *
     * @param methodName
     * @param tip
     * @return
     */
    private String createRemark(String methodName, String tip, Map<String, String> argMap) {
        StringBuffer remark = new StringBuffer(tip);


        return remark.toString();
    }
}
