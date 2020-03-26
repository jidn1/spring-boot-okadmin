package com.common.constants;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/19 16:30
 * @Description: 常量 HashKey
 */
public class ConstantsRedisKey {

    /**
     * session中存放用户信息的key值
     */
    public static final String SESSION_USER_INFO = "systemUser";

    /**
     * 桌面初始化 key值
     */
    public static final String DESKTOP = "desktop";

    /**
     * 会员人数
     */
    public static final String DESKTOP_PERSON_NUMBER = "personnumber";

    /**
     * 电影数量
     */
    public static final String DESKTOP_MOVIE_NUMBER = "movienumber";

    /**
     * 热榜数量
     */
    public static final String DESKTOP_HOT_NUMBER = "hotnumber";

    /**
     * 热搜 知乎
     */
    public static final String DESKTOP_ZH_NUMBER = "zhihu";

    /**
     * 基础配置
     */
    public static final String BASE_CONFIG = "baseConfig";

    /**
     * 爬虫存放地址
     */
    public static final String CRAW_URL = "crawUrl";

    /**
     * 前台电影缓存
     */
    public static final String VIDEO_MOVIE_LIST = "video:movie:list";

    /**
     * 前台porn缓存
     */
    public static final String VIDEO_PORN_LIST = "video:porn:list";

    public static final String VIDEO_PORN_TYPE = "video:porn:type";

}
