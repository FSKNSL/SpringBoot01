package com.example.service.rabbitmq.consumer;

import com.example.service.rabbitmq.entity.EventInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ModelConsumer {
    /*定义日志*/
    private static final Logger log= LoggerFactory.getLogger(ModelConsumer.class);
    @Autowired
    public ObjectMapper objectMapper;

    @RabbitListener(queues="${mq.fanout.queue.one.name}",containerFactory = "singleListenerContainer")

    public void consumeFanoutMsgOne(@Payload byte[] msg)
    {
        try{
            EventInfo info=objectMapper.readValue(msg,EventInfo.class);
            log.info("消息模型fanoutExchange-one-消费者-监听消费到消息:{}",info);


        }catch(Exception e)
        {
            log.error("消费模型-消费者-发生异常:",e.fillInStackTrace());
        }
    }

    @RabbitListener(queues="${mq.fanout.queue.two.name}",containerFactory = "singleListenerContainer")

    public void consumeFanoutMsgTwo(@Payload byte[] msg)
    {
        try{
            EventInfo info=objectMapper.readValue(msg,EventInfo.class);
            log.info("消息模型fanoutExchange-two-消费者-监听消费到消息:{}",info);


        }catch(Exception e)
        {
            log.error("消费模型-消费者-发生异常:",e.fillInStackTrace());
        }
    }
    @RabbitListener(queues="${mq.fanout.queue.one.name}",containerFactory = "singleListenerContainer")
    public void consumeFanoutMsgOne2(@Payload byte[] msg)
    {
        try{
           String message=new String(msg,"utf-8");
            log.info("消息模型fanoutExchange-one-消费者-监听消费到消息:{}",message);


        }catch(Exception e)
        {
            log.error("消费模型-消费者-发生异常:",e.fillInStackTrace());
        }
    }

    @RabbitListener(queues="${mq.fanout.queue.two.name}",containerFactory = "singleListenerContainer")

    public void consumeFanoutMsgTwo2(@Payload byte[] msg)
    {
        try{
            String message=new String(msg,"utf-8");
            log.info("消息模型fanoutExchange-two-消费者-监听消费到消息:{}",message);


        }catch(Exception e)
        {
            log.error("消费模型-消费者-发生异常:",e.fillInStackTrace());
        }
    }








    /*这是第一个路由绑定的对应队列的消费者方法*/
    /*监听队列中的消息-directExchange-one*/
   /* @RabbitListener(queues="${mq.direct.queue.one.name}",containerFactory = "singleListenerContainer")
    public void consumeDirectMsgOne(@Payload byte[] msg)
    {
        try{
            EventInfo  info=objectMapper.readValue(msg,EventInfo.class);
            log.info("消息模型directExchange-one-消费者-监听消费到消息:{}",info);


        }catch(Exception e)
        {
            log.error("消费模型directExchange-one-消费者-发生异常:",e.fillInStackTrace());
        }
    }
*/

    /*测试序列化字符串*/
    /*这是第二个路由绑定的对应队列的消费者方法*/
    /*监听队列中的消息-directExchange-one*/
    /*@RabbitListener(queues="${mq.direct.queue.two.name}",containerFactory = "singleListenerContainer")
    public void consumeDirectMsgTwo2(@Payload byte[] msg)
    {
        try{
            String message=new String(msg,"utf-8");
            log.info("消息模型directExchange-two-消费者-监听消费到消息:{}",message);


        }catch(Exception e)
        {
            log.error("消费模型directExchange-two-消费者-发生异常:",e.fillInStackTrace());
        }
    }*/


   /* @RabbitListener(queues="${mq.direct.queue.one.name}",containerFactory = "singleListenerContainer")
    public void consumeDirectMsgOne2(@Payload byte[] msg)
    {
        try{
            String message=new String(msg,"utf-8");
            log.info("消息模型directExchange-one-消费者-监听消费到消息:{}",message);


        }catch(Exception e)
        {
            log.error("消费模型directExchange-one-消费者-发生异常:",e.fillInStackTrace());
        }
    }*/


    /*这是第二个路由绑定的对应队列的消费者方法*/
    /*监听队列中的消息-directExchange-one*/
   /* @RabbitListener(queues="${mq.direct.queue.two.name}",containerFactory = "singleListenerContainer")
    public void consumeDirectMsgTwo(@Payload byte[] msg)
    {
        try{
            EventInfo  info=objectMapper.readValue(msg,EventInfo.class);
            log.info("消息模型directExchange-two-消费者-监听消费到消息:{}",info);


        }catch(Exception e)
        {
            log.error("消费模型directExchange-two-消费者-发生异常:",e.fillInStackTrace());
        }
    }*/






}
