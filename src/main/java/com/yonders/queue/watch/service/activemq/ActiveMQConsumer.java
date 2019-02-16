package com.yonders.queue.watch.service.activemq;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Service
@Log4j2
public class ActiveMQConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {

        try {
            process(message);
        } catch (JMSException ex) {
            log.error("Error processing message in queue.");
        }
    }

    private void process(Message message) throws JMSException {

        System.out.println(((TextMessage) message).getText());
    }
}
