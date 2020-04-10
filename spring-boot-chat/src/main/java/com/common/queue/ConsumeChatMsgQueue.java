package com.common.queue;

import com.chat.service.ChatUserService;
import com.chat.vo.ChatMsgVo;
import com.util.PropertiesUtils;
import com.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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



    @PostConstruct
    public void startThread() {
        ExecutorService e = Executors.newFixedThreadPool(1);// 两个大小的固定线程池
        e.submit(new PollChatMsg());
    }

    class PollChatMsg implements Runnable {
        private ChatUserService chatUserService = (ChatUserService) SpringUtil.getBean(PropertiesUtils.APP.getProperty("app.service"));

        public PollChatMsg() {
        }

        @Override
        public void run() {
            while (true) {
                try {
                    ChatMsgVo chatMsgVo = ChatQueue.getMailQueue().consume();
                    if (chatMsgVo != null) {
                        logger.info("聊天消息队列剩余数量:{}",ChatQueue.getMailQueue().size());
                        chatUserService.saveMsg(chatMsgVo);
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
