package com.demo.mq;

/**
 * Created by jzg on 2017-05-20.
 */
public interface MQProducer {
    public void sendDataToQueue(String queueKey, Object object);
}
