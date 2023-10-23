package com.example.service.utils;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedPacketTest {
    /*定义日志*/
    private static final Logger log= LoggerFactory.getLogger(RedPacketTest.class);
    /*二倍均值自测方法*/
    @Test
    public void one() throws Exception{
        Integer amount=1000;
        Integer total=10;
        List<Integer> list=RedPacketUtil.divideRedPacketage(amount,total);
        log.info("总金额={}分,总个数={}个",amount,total);
        Integer sum=0;
        for(Integer i:list)
        {
            log.info("随机金额为:{}分，即{}元",i,new BigDecimal(i.toString()).divide(new BigDecimal(100)));
            sum+=i;
        }
        log.info("所有随机金额叠加之和={}分",sum);


    }




}