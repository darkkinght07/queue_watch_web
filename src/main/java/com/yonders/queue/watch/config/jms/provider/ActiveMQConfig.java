package com.yonders.queue.watch.config.jms.provider;

import com.yonders.queue.watch.service.activemq.ActiveMQConsumer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

@Configuration
@Profile("activemq")
public class ActiveMQConfig {

    @Value("${activemq.broker.url}")
    private String brokerURL;

    @Value("${connection.uri}")
    private String connectionURI;

    @Value("${connection.username}")
    private String username;

    @Value("${connection.password}")
    private String password;

    @Autowired
    private ActiveMQConsumer consumer;

    @Bean("sessionConsumer")
    public Session session() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setUseAsyncSend(true);
        factory.setBrokerURL(brokerURL);

//        JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI); // for amqp
        Connection connection = factory.createConnection(username, password);
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("Ion");
        MessageConsumer receiver = session.createConsumer(queue);
        receiver.setMessageListener(consumer);

        return session;
    }

    @Bean("sessionProducer")
    public Session sessionProducer() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setUseAsyncSend(true);
        factory.setBrokerURL(brokerURL);

        Connection connection = factory.createConnection(username, password);
        connection.start();

        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

        return session;
    }
}
