package com.yonders.queue.watch.controller;

import com.yonders.queue.watch.dto.MessageQueueConfig;
import com.yonders.queue.watch.dto.QueueStatistic;
import com.yonders.queue.watch.service.ConsumerService;
import com.yonders.queue.watch.service.ProducerService;
import com.yonders.queue.watch.service.QueueStatsService;
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
    private ProducerService producerService;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private QueueStatsService queueStatsService;

    @GetMapping("info")
    public QueueStatistic info(@RequestParam("queueName") String queueName,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password) {
        return queueStatsService.getStatistics(queueName, username, password);
    }

    @PostMapping("send")
    public String send(@RequestBody MessageQueueConfig queueConfig) throws JMSException {

        return producerService.createMessages(queueConfig);
    }

    @GetMapping("receive")
    public String receive(@RequestParam("queueName") String queueName,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password) {

        consumerService.createConsumer(queueName, username, password);

        return "ConsumerService created. Waiting for messages.";
    }
}
