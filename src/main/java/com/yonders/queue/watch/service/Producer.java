package com.yonders.queue.watch.service;

import com.yonders.queue.watch.dto.MessageQueueConfig;

import javax.jms.JMSException;

public interface Producer {

    void createMessages(MessageQueueConfig messageQueueConfig) throws JMSException;
}

