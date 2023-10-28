package com.example.service.rabbitmq.entity;

import com.example.service.rabbitmq.publisher.BasicPublisher;
import com.example.service.rabbitmq.publisher.ModelPublisher;
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

public class RabbitmqTest {
    /*定义日志*/
    private static final Logger log= LoggerFactory.getLogger(RabbitmqTest.class);
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    BasicPublisher basicPublisher;
    @Autowired
    ModelPublisher modelPublisher;

    @Test
    public void test3() throws Exception
    {

        EventInfo info=new  EventInfo(1,"增删改查模块","基于fanoutExchange的消息模型","这是基于fanoutExchange的消息模型");
        modelPublisher.sendMsg(info);
    }
    @Test
    public void test3plus() throws Exception
    {
        String msg="~~~~~hahaha这是一条字符串测试信息~~~";
        modelPublisher.sendMsg2(msg);
    }



    @Test
    public void test4() throws Exception
    {

        EventInfo info=new  EventInfo(1,"增删改查模块-1","基于directExchange的消息模型-1","这是基于directExchange的消息模型-1");
        modelPublisher.sendMsgDirectOne(info);
        info=new  EventInfo(2,"增删改查模块-2","基于directExchange的消息模型-2","这是基于directExchange的消息模型-2");
        modelPublisher.sendMsgDirectTwo(info);
    }
    @Test
    public void test4plus() throws Exception
    {

        String msg="~~~~~hahaha这是第一条字符串测试信息~~~";
        modelPublisher.sendMsgDirectOne2(msg);
        String msg2="~~~~~hahaha这是第二条字符串测试信息~~~";
        modelPublisher.sendMsgDirectTwo2(msg2);
    }




}