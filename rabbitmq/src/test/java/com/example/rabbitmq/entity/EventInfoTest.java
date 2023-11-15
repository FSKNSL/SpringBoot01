package com.example.rabbitmq.entity;

import com.example.rabbitmq.rabbitmq.publisher.BasicPublisher;
import com.example.rabbitmq.rabbitmq.publisher.ModelPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EventInfoTest {
    private static final Logger log= LoggerFactory.getLogger(EventInfoTest.class);
    @Autowired

    ObjectMapper objectMapper;
    @Autowired
    BasicPublisher basicPublisher;
    @Autowired
    ModelPublisher modelPublisher;



    @Test
    public void test4() throws Exception
    {

        EventInfo info=new  EventInfo(1,"增删改查模块-1","基于directExchange的消息模型-1","这是基于directExchange的消息模型-1");
        modelPublisher.sendMsgDirectOne(info);
        info=new  EventInfo(2,"增删改查模块-2","基于directExchange的消息模型-2","这是基于directExchange的消息模型-2");
        modelPublisher.sendMsgDirectTwo(info);
    }

}