package com.example.rabbitmq.rabbitmq.consumer;


import com.example.rabbitmq.service.ISysLogService;

import com.example.service.model.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
public class RedrobConsumer {
    private static final Logger log=LoggerFactory.getLogger(RedrobConsumer.class);
    @Autowired
    ObjectMapper objectMapper;
    /*接口*/
    @Autowired(required = false)
    private ISysLogService sysLogService;

    @RabbitListener(queues="${mq.direct.queue.one.name}",containerFactory = "singleListenerContainer")
    public void consumeDirectMsgOne(@Payload byte[] msg)
    {
        try{
            Msg message=objectMapper.readValue(msg,Msg.class);
            log.info("基于消息模型directExchange抢红包消费者-监听消费到消息:{}",message);
/*调用日志记录服务-用于记录用户抢单成功后的消息-内容{}*/

          sysLogService.recordLog(message);
        }catch(Exception e)
        {
            log.error("基于消费模型directExchange抢红包消费者-发生异常:",e.fillInStackTrace());
        }
    }


    @RabbitListener(queues="${mq.direct.queue.two.name}",containerFactory = "singleListenerContainer")
    public void consumeDirectMsgTwo(@Payload byte[] msg)
    {
        try{
            String message=new String(msg,"utf-8");
            log.info("基于消息模型directExchange抢红包消费者-监听消费到消息:{}",message);


        }catch(Exception e)
        {
            log.error("基于消费模型directExchange抢红包消费者-发生异常:",e.fillInStackTrace());
        }
    }






}
