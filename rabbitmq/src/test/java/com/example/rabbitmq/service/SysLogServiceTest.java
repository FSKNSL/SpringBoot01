package com.example.rabbitmq.service;


import com.example.rabbitmq.dao.SysLogDao;
import com.example.service.model.Msg;


import model.SysLog;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SysLogServiceTest {
    private static final Logger log= LoggerFactory.getLogger(SysLogServiceTest.class);

    @Autowired(required = false)
    private SysLogDao sysLogDao;

    @Test
    public void  testdatabase()
    {
           Msg msg=new Msg();
           msg.setRedId("redis:red:packet10010:9858178029900");
           msg.setResult(BigDecimal.valueOf(1.09));
                  msg.setUserId(Integer.valueOf("10010"));
        try{
            /* private Integer userId;*//*用户id*//*
        private String redId;*//*红包标识符*//*
        private BigDecimal result;*//*抢到的金额*/
            SysLog sysLog=new SysLog();
            sysLog.setModule("用户抢单模块");
            sysLog.setAmount(msg.getResult().multiply(new BigDecimal(100))); // 将结果乘以100
            sysLog.setRedPacket(String.valueOf(msg.getRedId())); // 将结果转换为String类型
            sysLog.setMemo("用户"+msg.getUserId()+"抢单成功记录抢单信息");
            sysLog.setCreateTime(new Date());
           sysLogDao.insertSelective(sysLog);

        }catch (Exception e)
        {
            log.error("系统日志服务-记录用户抢单成功信息入数据库发生异常:{}\n{}",msg,e.getMessage());
            e.printStackTrace();
        }
    }

   /* *
    private Integer id;
    private String module;
    private BigDecimal amount;
    private String redPacket;
    private String memo;
    private Date createTime;
*/
    @Test
    public void testdatabase2()
    {

        SysLog sysLog=new SysLog();
        sysLog.setModule("test");
        sysLog.setAmount(BigDecimal.valueOf(100));
        sysLog.setRedPacket("10010");
        sysLog.setMemo("100");
        sysLog.setCreateTime(new Date());
        sysLogDao.insertSelective(sysLog);

    }



}