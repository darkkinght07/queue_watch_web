package com.yonders.queue.watch.service;

import com.yonders.queue.watch.dto.QueueStatistic;

public interface QueueStatsService {

    QueueStatistic getStatistics(String queueName, String username, String password);
}
