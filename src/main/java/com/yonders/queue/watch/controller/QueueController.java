package com.yonders.queue.watch.controller;

import com.yonders.queue.watch.dto.MessageQueueConfig;
import com.yonders.queue.watch.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private Producer producer;

    @GetMapping("info")
    public String getQueueInfo() {
        return "queue is up";
    }

    @PostMapping("create")
    public String createQueue(@RequestBody MessageQueueConfig queueConfig) throws JMSException {

        producer.createMessages(queueConfig);

        return "Messages sent";
    }
}
