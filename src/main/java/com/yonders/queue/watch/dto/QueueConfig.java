package com.yonders.queue.watch.dto;

import lombok.Data;

@Data
public class QueueConfig {
    private String queueName;
    private int number = 1;
}
