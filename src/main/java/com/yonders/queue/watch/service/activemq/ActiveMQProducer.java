package com.yonders.queue.watch.service.activemq;

import com.yonders.queue.watch.dto.MessageQueueConfig;
import com.yonders.queue.watch.service.Producer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
@Log4j2
public class ActiveMQProducer implements Producer {

    @Autowired
    @Qualifier("sessionProducer")
    private Session session;

    public void createMessages(MessageQueueConfig messageQueueConfig) throws JMSException {
        Destination destination = session.createQueue(messageQueueConfig.getQueueName());

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i = 1; i <= messageQueueConfig.getNumber(); i++) {
            TextMessage msg = session.createTextMessage(messageQueueConfig.getMessage());
            producer.send(msg);
        }

        session.commit();

        log.debug(String.format("Total sent %d messages", messageQueueConfig.getNumber()));
    }
}
