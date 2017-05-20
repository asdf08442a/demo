package com.demo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import javax.annotation.Resource;

/**
 * Created by jzg on 2017-05-20.
 */
public class MQProducerImpl implements MQProducer {
    private final static Logger logger = LoggerFactory.getLogger(MQProducerImpl.class);
    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendDataToQueue(String queueKey, Object object) {
        try {
            amqpTemplate.convertAndSend(queueKey, object);
        } catch (Exception e) {
            logger.error("异常信息：{}", e);
        }
    }
}
