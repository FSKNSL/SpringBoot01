package com.example.rabbitmq.rabbitmq.publisher;

import com.example.rabbitmq.entity.EventInfo;
import com.example.rabbitmq.rabbitmq.consumer.ModelConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



@Component
public class ModelPublisher {
    private  static final Logger log= LoggerFactory.getLogger(ModelPublisher.class);
   @Autowired
    private ObjectMapper  objectMapper;
   @Autowired
    RabbitTemplate rabbitTemplate;
   @Autowired
    private Environment env;
    /*发送消息-基于DirectExchange消息模型-one*/
    public void sendMsgDirectOne(EventInfo info)
    {
        if(info!=null)
        {
            try{
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.one.name"));
                Message msg= MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("消息模型DirectExchange-one生产者-发送消息:{}",info);

            }catch(Exception e)
            {
                log.error("消息模型DirectExchange-one生产者--发送消息发生异常:{}",info,e.fillInStackTrace());
            }
        }
    }
    /*发送消息-基于DirectExchange消息模型-one*/
    public void sendMsgDirectTwo(EventInfo info)
    {
        if(info!=null)
        {
            try{
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.two.name"));
                Message msg= MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("消息模型DirectExchange-two生产者-发送消息:{}",info);

            }catch(Exception e)
            {
                log.error("消息模型DirectExchange-two生产者--发送消息发生异常:{}",info,e.fillInStackTrace());
            }
        }
    }


}
