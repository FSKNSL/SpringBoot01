package com.example.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.rabbitmq"
})
@MapperScan(basePackages = {"com/example/rabbitmq/dao"})
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }

}


/*@SpringBootApplication (scanBasePackages = {"com.example.service"
      })
@MapperScan(basePackages = {"com/example/service/dao"}) //设置MyBatis DAO包*/