package com.yonders.queue.watch.service.activemq;

import com.yonders.queue.watch.dto.MessageQueueConfig;
import com.yonders.queue.watch.service.ProducerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

@Service
@Log4j2
public class ActiveMQProducerServiceImpl implements ProducerService {

    @Autowired
    private ConnectionFactory factory;

    public String createMessages(MessageQueueConfig messageQueueConfig) throws JMSException {
        Connection connection = factory.createConnection(
                messageQueueConfig.getUsername(), messageQueueConfig.getPassword());
        connection.start();

        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

        Destination destination = session.createQueue(messageQueueConfig.getQueueName());

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        createAndSend(messageQueueConfig, session, producer);

        session.commit();

        return String.format("Total sent %d messages", messageQueueConfig.getNumber());
    }

    private void createAndSend(MessageQueueConfig messageQueueConfig, Session session, MessageProducer producer)
            throws JMSException {

        for (int i = 0; i < messageQueueConfig.getNumber(); i++) {
            Message msg = session.createTextMessage(messageQueueConfig.getMessage());
            producer.send(msg);
        }
    }
}
