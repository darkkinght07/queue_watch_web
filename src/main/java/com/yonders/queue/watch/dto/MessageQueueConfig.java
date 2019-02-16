package com.yonders.queue.watch.dto;

import lombok.Data;

@Data
public class MessageQueueConfig {
    private String queueName;
    private String message;
    private int number = 1;

    private String username;
    private String password;
}
