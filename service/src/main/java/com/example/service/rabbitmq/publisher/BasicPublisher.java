package com.example.service.rabbitmq.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;

import java.nio.charset.StandardCharsets;

@Component
public class BasicPublisher {
    private final Logger logger = LoggerFactory.getLogger(BasicPublisher.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;

    public void sendMessage(String message){
        if(message == null || message.length() == 0) return;
        //Strings....
        try{
            //1. settings: exchange, binding(routing)
            //JSON
            rabbitTemplate.setExchange(env.getProperty("mq.basic.info.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mq.basic.info.routing.key.name"));
            //rabbitTemplate.setConfirmCallback();//TODO:
            //2. message buid
            Message msg = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            //3. send
            rabbitTemplate.convertAndSend(msg);
            logger.info("Send basic ok:" + message);
        }catch (Exception e){
            logger.info("exception:" + e.getStackTrace());
        }
    }
}
