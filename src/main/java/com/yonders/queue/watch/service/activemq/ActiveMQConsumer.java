package com.yonders.queue.watch.service.activemq;

import com.yonders.queue.watch.service.Consumer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
@Log4j2
public class ActiveMQConsumer extends Consumer {

    @Autowired
    private ConnectionFactory factory;

    public void createConsumer(String queueName, String username, String password) {
        try {
            Connection connection = factory.createConnection(username, password);
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageConsumer receiver = session.createConsumer(queue);
            receiver.setMessageListener(this);

        } catch (JMSException ex) {
            log.error("Error creating queue: " + queueName);
        }
    }

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
