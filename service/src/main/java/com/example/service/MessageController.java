package com.example.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import  test.Message;
@RestController
public class MessageController {
    @RequestMapping("/test/msg")
    public Message helloMsg()
    {
//        1.非链式
//        Message msg=new Message();
//        msg.setCode("001");
//        msg.setMsg("Hello SpringBoot!");
//        return  msg;
//        2.链式
        return new Message().setCode("001").setMsg("Hello SpringBoot!hello world!软构建与中间件");
    }
}

