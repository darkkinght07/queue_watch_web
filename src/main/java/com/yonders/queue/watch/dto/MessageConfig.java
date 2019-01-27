package com.yonders.queue.watch.dto;

import lombok.Data;

@Data
public class MessageConfig {
    private String message;
    private String queueName;
}
