package com.example.rabbitmq.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BasicConsumer {
    private Logger logger = LoggerFactory.getLogger(BasicConsumer.class);

    @RabbitListener(queues = "${mq.basic.info.queue.name}",
            containerFactory = "singleListenerContainer")
    public void onBasicMessage(@Payload byte[] msg) {
        try {
            String message = new String(msg, "utf-8");
            logger.info("receive basic msg:" + message);
        } catch (Exception e) {
            logger.info("exception:" + e.getStackTrace());
        }
    }
}