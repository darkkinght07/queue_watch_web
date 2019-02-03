package com.yonders.queue.watch.controller;

import com.yonders.queue.watch.dto.MessageConfig;
import com.yonders.queue.watch.dto.QueueConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

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
        Destination destination = session.createQueue(queueConfig.getQueueName());

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for (int i = 1; i <= queueConfig.getNumber(); i++) {
            TextMessage msg = session.createTextMessage("#:" + i);
            msg.setIntProperty("id", i);
            producer.send(msg);
            if ((i % 50000) == 0) {
                System.out.println(String.format("Sent %d messages", i));
            }
        }

        System.out.println(String.format("Total sent %d messages", queueConfig.getNumber()));

        return "Messages sent";
    }

    @GetMapping("receive")
    public String receive(@RequestParam("queueName") String queueName) throws JMSException {

        Queue queue = session.createQueue(queueName);

        MessageConsumer consumer = session.createConsumer(queue);

        long count = 1;
        System.out.println("Waiting for messages...");

        Message receive = consumer.receive();
        while (receive != null) {
            receive = consumer.receive();

            if (count % 50000 == 0) {
                System.out.println(String.format("Received %d messages.", count));
            }
            count++;
        }

        System.out.println(String.format("All messages %d received.", count));

        return "All messages received.";
    }
}
