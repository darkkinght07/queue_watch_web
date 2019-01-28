package com.yonders.queue.watch.controller;

import com.yonders.queue.watch.dto.MessageConfig;
import com.yonders.queue.watch.dto.QueueConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import javax.jms.Session;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private Session session;

    @GetMapping("info")
    public String getQueueInfo() {
        return "queue is up";
    }

    @PostMapping("send")
    public String sendMessageToQueue(@RequestBody MessageConfig messageConfig) {
        return "sending messages to queue " + messageConfig.toString();
    }

    @PostMapping("create")
    public String createQueue(@RequestBody QueueConfig queueConfig) throws JMSException {
        session.createQueue(queueConfig.getQueueName());
//        session.commit();

        return "Queue name `" + queueConfig.getQueueName() + "` created.";
    }

//    Queue destination = session.createQueue("ION");
//
//        MessageProducer producer = session.createProducer(destination);
//        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//
//        MessageConsumer consumer = session.createConsumer(destination);
//
//        TextMessage message = session.createTextMessage();
//        message.setText("Ion da best haker");
//
//        producer.send(message);
//
//        TextMessage receivedMessage = (TextMessage)consumer.receive();
//        System.out.println(receivedMessage.getText());
//
//        return session;
}
