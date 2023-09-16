package com.example.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import test.Message;

@RestController
public class MyMsgController {
    private final  String URLPREFIX="http://localhost:6001";
    /*访问的是service中的服务*/
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/my/msg")
    public Message testMsg()
    {
//        作为客户端使用,访问service中的内容
        return  restTemplate.getForObject(URLPREFIX+"/test/msg",Message.class);
    }
}
