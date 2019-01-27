package com.yonders.queue.watch.controller;

import com.yonders.queue.watch.dto.MessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jms.ConnectionFactory;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private ConnectionFactory connectionFactory;

    @GetMapping("info")
    public String getQueueInfo() {
        return "queue is up";
    }

    @PostMapping("send")
    public String sendMessageToQueue(@RequestBody MessageConfig messageConfig) {
        return "sending messages to queue " + messageConfig.toString();
    }
}
