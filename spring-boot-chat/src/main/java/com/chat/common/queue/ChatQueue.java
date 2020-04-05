package com.chat.common.queue;

import com.chat.vo.ChatMsgVo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Copyright: 正经吉
 * @author: ChatQueue
 * @version: V1.0
 * @Date: 2020/4/5
 */
public class ChatQueue {

    //队列大小
    static final int QUEUE_MAX_SIZE   = 1000;

    static BlockingQueue<ChatMsgVo> blockingQueue = new LinkedBlockingQueue<ChatMsgVo>(QUEUE_MAX_SIZE);

    /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private ChatQueue(){};
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private  static ChatQueue queue = new ChatQueue();
    }
    //单例队列
    public static ChatQueue getMailQueue(){
        return SingletonHolder.queue;
    }
    //生产入队
    public static void  produce(ChatMsgVo chatMsgVo) {
        try {
            blockingQueue.put(chatMsgVo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //消费出队
    public  ChatMsgVo consume() throws InterruptedException {
        return blockingQueue.take();
    }
    // 获取队列大小
    public int size() {
        return blockingQueue.size();
    }

}
