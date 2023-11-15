package com.example.rabbitmq;


import com.example.rabbitmq.rabbitmq.publisher.BasicPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private BasicPublisher basicPublisher;

    @RequestMapping("/msg/send")
    public String basicSend() {
        basicPublisher.sendMessage("alskdfjopqw84usoadfh");
        return "Send ok.";

    }
}
