package com.yonders.queue.watch.controller;

import com.yonders.queue.watch.dto.MessageQueueConfig;
import com.yonders.queue.watch.service.Consumer;
import com.yonders.queue.watch.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;

    @GetMapping("info")
    public String getQueueInfo() {
        return "queue is up";
    }

    @PostMapping("send")
    public String createQueue(@RequestBody MessageQueueConfig queueConfig) throws JMSException {

        producer.createMessages(queueConfig);

        return "Messages sent";
    }

    @GetMapping("receive")
    public String receive(@RequestParam("queueName") String queueName,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password) {

        consumer.createConsumer(queueName, username, password);

        return "Consumer created. Waiting for messages.";
    }
}
