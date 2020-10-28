package com.thread;

import spring.org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/30 18:52
 * @Description: 线程池
 */
public class ThreadPool {

    private static final String THREAD_NAME = "Boot-Core-Thread-%d";

    private static final Integer POOL_MIN = 3;

    private static final Integer POOL_SIZE = 8;

    private static ThreadPoolExecutor threadPool;

    static {
        threadPool = new ThreadPoolExecutor(POOL_MIN, POOL_SIZE,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new BasicThreadFactory.Builder().namingPattern(THREAD_NAME).build());
    }

    /**
     * 运行线程
     *
     * @param runnable 传入一个new好的线程
     */
    public static void exe(Runnable runnable) {
        threadPool.execute(runnable);
    }
}
