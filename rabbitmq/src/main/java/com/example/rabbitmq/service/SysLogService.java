package com.example.rabbitmq.service;




import com.example.service.model.Msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.rabbitmq.dao.SysLogDao;
import model.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/*系统日志服务*/
@Service
@EnableAsync
public class SysLogService  implements ISysLogService {
    /*定义日志*/
    private static final Logger log= LoggerFactory.getLogger(SysLogService.class);
    /*定义系统日志操作接口*/


    @Autowired
    private ObjectMapper objectMapper;
 @Autowired(required = false)
 private SysLogDao sysLogDao;

    /*将抢单成功的信息计入数据库*/

    @Override
    @Async
    @Transactional(rollbackFor=Exception.class)
    public void recordLog(Msg msg)
    {
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

            System.out.println("sysLog: " + sysLog);
            /*对象信息插入数据库*/

      sysLogDao.insertSelective(sysLog);

        }catch (Exception e)
        {
            log.error("系统日志服务-记录用户抢单成功信息入数据库发生异常:{}",msg,e.getMessage());
            e.printStackTrace();
        }

    }

}
