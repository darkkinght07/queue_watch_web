package com.yonders.queue.watch.config.jms.provider;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.jms.ConnectionFactory;

@Configuration
@Profile("activemq")
public class ActiveMQConfig {

    @Value("${activemq.broker.url}")
    private String brokerURL;

    @Value("${connection.uri}")
    private String connectionURI;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setUseAsyncSend(true);
        factory.setBrokerURL(brokerURL);

//        ConnectionFactory factory = new JmsConnectionFactory(connectionURI); // for amqp

        return factory;
    }
}
