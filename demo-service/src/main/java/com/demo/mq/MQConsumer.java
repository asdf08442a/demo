package com.demo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by jzg on 2017-05-20.
 */
public class MQConsumer implements MessageListener {
    private final static Logger logger = LoggerFactory.getLogger(MQConsumer.class);

    @Override
    public void onMessage(Message message) {
        try {
            logger.info("处理消息：{}", message.toString());
            //...
        } catch (Exception e) {
            logger.error("处理异常：{}", e);
        }
    }
}
