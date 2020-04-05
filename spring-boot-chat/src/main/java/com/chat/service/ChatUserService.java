package com.chat.service;



import com.chat.common.utils.JsonResult;

import java.util.Map;

/**
 * <p> ChatUserService </p>
 * @author:         jidn
 * @Date :          2020-04-03 16:10:39 
 */
public interface ChatUserService {

   JsonResult login(String username, String password);

}
