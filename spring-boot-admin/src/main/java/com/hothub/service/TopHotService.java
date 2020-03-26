package com.hothub.service;


import com.hothub.model.TopHot;
import com.common.model.PageResult;

import java.util.Map;

/**
 * <p> TopHotService </p>
 *
 * @author: jidn
 * @Date :          2019-12-26 17:33:13
 */
public interface TopHotService {

    /**
     * 分页
     *
     * @param param
     * @return
     */
    PageResult findPageBySql(Map<String, String> param);

    /**
     * 爬取博客园
     */
    public void crawlerCnBlog();

    /**
     * 爬取百度热搜
     */
    public void crawlerBaiDu();

    /**
     * 爬取知乎
     */
    public void crawlerZhiHu();

    /**
     * 爬取虎扑
     */
    public void crawlerBbs();

    /**
     * 爬取it之家
     */
    public void crawlerItHome();
}
