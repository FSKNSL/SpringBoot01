package com.example.service.controller;

import com.example.service.dto.RedPacketDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.service.service.IRedPacketService;
import test.BaseResponse;
import test.StatusCode;
import org.slf4j.Logger;

import java.math.BigDecimal;


/*红包处理逻辑Controller*/
@RestController
public class RedPacketController {
    private static final Logger log= (Logger)LoggerFactory.getLogger(RedPacketController.class);
    /*定义请求路径的前缀*/
    private static final String prefix="red/packet";
    /*注入红包业务逻辑处理接口服务*/
    @Autowired
    private IRedPacketService redPacketService;

@Autowired
private ObjectMapper objectMapper;
@Autowired
private RabbitTemplate rabbitTemplate;
@Autowired
private Environment env;





    /*发红包请求-请求方法为Post,请求参数采用JSON格式进行交互*/
    @RequestMapping(value=prefix+"/hand/out",method= RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)

    public BaseResponse handOut(@Validated @RequestBody RedPacketDto dto, BindingResult result)
    /*参数检验*/
    {

        if(result.hasErrors())
        {
            return new BaseResponse(StatusCode.InvalidParams);

        }
        BaseResponse response=new BaseResponse(StatusCode.Success);


    try {
        /*核心业务逻辑处理服务-最终返回红包全局唯一标识串*/

        String redId = redPacketService.handOut(dto);
        response.setData(redId);

    }catch(Exception e){
        log.error("发红包异常:dto={}",dto,e.fillInStackTrace());
       response= new  BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
    }
    return response;
    }

    /*处理抢红包请求:接收当前用户账户id和全局唯一标识串参数*/
    @RequestMapping(value=prefix+"/rob",method=RequestMethod.GET)
    public BaseResponse rob(@RequestParam Integer userId,@RequestParam String redId)
    {
        /*定义响应对象*/
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try{
            /*调用红包业务逻辑接口中的抢红包办法，最终返回抢到的红包金额*/
            /*单位为元,不为null表示抢到了,否则代表已经被抢完了*/
            BigDecimal result=redPacketService.rob(userId, redId);
            if(result!=null)
            {
                response.setData(result);
            }else
            {
                String message="红包已经被抢完";
                try{
                    response=new BaseResponse(StatusCode.Fail.getCode(),"红包已被抢完!");
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                    rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.two.name"));
                    Message msg= MessageBuilder.withBody(message.getBytes("utf-8")).build();
                    rabbitTemplate.convertAndSend(msg);
                    log.info("基于消息模型DirectExchange抢红包-发送消息:{}",message);
                }catch(Exception e)
                {
                    log.error("基于消息模型DirectExchange抢红包--发送消息发生异常:{}",message,e.fillInStackTrace());
                }



            }
        }catch(Exception e)
        {
            /*处理过程如发生异常，则打印异常信息并返回给前端*/
            log.error("抢红包发生异常:userId={} redId={}",userId,redId,e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        /*返回处理结果给前端*/
        return  response;

    }

}
