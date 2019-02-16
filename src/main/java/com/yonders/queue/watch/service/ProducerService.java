package com.yonders.queue.watch.service;

import com.yonders.queue.watch.dto.MessageQueueConfig;

import javax.jms.JMSException;

public interface ProducerService {

    String createMessages(MessageQueueConfig messageQueueConfig) throws JMSException;
}

