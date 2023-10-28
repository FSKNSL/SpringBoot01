package com.example.service.rabbitmq.publisher;

import com.example.service.rabbitmq.entity.EventInfo;
import com.fasterxml.jackson.databind.ObjectMapper;


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
    /*定义日志*/
    private  static final Logger log= LoggerFactory.getLogger(ModelPublisher.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;


    /*测试对象*/
    public void sendMsg(EventInfo info)
    {
        if(info!=null)
        {
            try{
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.fanout.exchange.name"));
                Message msg= MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("消息模型fanoutExchange-生产者-发送消息:{}",info);

            }catch(Exception e)
            {
                log.error("消息模型fanoutExchange-生产者--发送消息发生异常:{}",info,e.fillInStackTrace());
            }
        }
    }

    /*测试序列化字符串*/
    public void  sendMsg2(String message)
    {
        /*判断字符串是否为空*/
        if(!Strings.isEmpty(message))
        {
            try{
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                /*指定交换机*/
                rabbitTemplate.setExchange(env.getProperty("mq.fanout.exchange.name"));
              Message msg=MessageBuilder.withBody(message.getBytes("utf-8")).build();
rabbitTemplate.convertAndSend(msg);
                log.info("消息模型fanoutExchange-生产者-发送消息:{}",message);

            }catch(Exception e)
            {
                log.error("消息模型fanoutExchange-生产者--发送消息发生异常:{}",message,e.fillInStackTrace());
            }
        }
    }

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


    /*测试序列化字符串信息*/
    public void sendMsgDirectOne2(String message)
    {
        if(!Strings.isEmpty(message))
        {
            try{
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.one.name"));
                Message msg=MessageBuilder.withBody(message.getBytes("utf-8")).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("消息模型DirectExchange-one生产者-发送消息:{}",message);

            }catch(Exception e)
            {
                log.error("消息模型DirectExchange-one生产者--发送消息发生异常:{}",message,e.fillInStackTrace());
            }
        }
    }
    /*发送消息-基于DirectExchange消息模型-one*/
    public void sendMsgDirectTwo2(String message)
    {
        if(!Strings.isEmpty(message))
        {
            try{
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.two.name"));
                Message msg=MessageBuilder.withBody(message.getBytes("utf-8")).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("消息模型DirectExchange-two生产者-发送消息:{}",message);

            }catch(Exception e)
            {
                log.error("消息模型DirectExchange-two生产者--发送消息发生异常:{}",message,e.fillInStackTrace());
            }
        }
    }



}
