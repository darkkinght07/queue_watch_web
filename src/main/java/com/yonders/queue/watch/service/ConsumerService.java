package com.yonders.queue.watch.service;

import javax.jms.MessageListener;

public abstract class ConsumerService implements MessageListener {

    public abstract void createConsumer(String queueName, String username, String password);
}
