package com.yonders.queue.watch.service.activemq;

import com.yonders.queue.watch.dto.QueueStatistic;
import com.yonders.queue.watch.service.QueueStatsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
@Log4j2
public class QueueStatsServiceImpl implements QueueStatsService {

    private static final String JMS_CONNECTION_ERROR = "Error creating a jms connection.";

    @Autowired
    private ConnectionFactory factory;

    @Override
    public QueueStatistic getStatistics(String queueName, String username, String password) {

        try {
            Connection connection = factory.createConnection(username, password);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();

            Queue queue = session.createQueue(queueName);
            QueueBrowser browser = session.createBrowser(queue);

            Enumeration enumeration = browser.getEnumeration();
            return getStats(enumeration);

        } catch (JMSException e) {
            log.error(JMS_CONNECTION_ERROR, e);
        }

        return new QueueStatistic(new ArrayList<>(), JMS_CONNECTION_ERROR);
    }

    private QueueStatistic getStats(Enumeration enumeration) throws JMSException {
        List<String> messageList = new ArrayList();
        int size = 0;

        while (enumeration.hasMoreElements()) {
            Object element = enumeration.nextElement();

            if (element instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) element;
                messageList.add(textMessage.getText());
            }

            size++;
        }

        return new QueueStatistic(messageList, getInfoMessage(size, messageList.size()));
    }

    private String getInfoMessage(int allMessagesSize, int textMessagesSize) {

        if (allMessagesSize != textMessagesSize) {
            return textMessagesSize + " text messages displayed from " + allMessagesSize + ". Non-text messages are not displayed.";
        } else {
            return "All messages are text messages and all are displayed in the above list.";
        }
    }
}
