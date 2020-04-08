package com.common.queue;

import com.chat.service.ChatMessageService;
import com.chat.vo.ChatMsgVo;
import org.nutz.dao.entity.annotation.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Copyright: 正经吉
 * @author: ConsumeChatMsgQueue
 * @version: V1.0
 * @Date: 2020/4/5
 */
@Component
public class ConsumeChatMsgQueue {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeChatMsgQueue.class);

    @Resource
    ChatMessageService chatMessageService;

    @PostConstruct
    public void startThread() {
        ExecutorService e = Executors.newFixedThreadPool(2);// 两个大小的固定线程池
        e.submit(new PollChatMsg(chatMessageService));
        e.submit(new PollChatMsg(chatMessageService));
    }

    class PollChatMsg implements Runnable {
        ChatMessageService chatMessageService;

        public PollChatMsg(ChatMessageService chatMessageService) {
            this.chatMessageService = chatMessageService;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    ChatMsgVo chatMsgVo = ChatQueue.getMailQueue().consume();
                    if (chatMsgVo != null) {
                        logger.info("聊天消息队列剩余数量:{}",ChatQueue.getMailQueue().size());
                        chatMessageService.save(chatMsgVo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @PreDestroy
    public void stopThread() {
        logger.info("destroy");
    }
}
