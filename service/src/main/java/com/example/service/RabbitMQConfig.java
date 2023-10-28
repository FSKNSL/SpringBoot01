package com.example.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitMQConfig {
    private static final Logger log= LoggerFactory.getLogger(RabbitMQConfig.class);
    @Autowired(required = false)
    private CachingConnectionFactory cachingConnectionFactory; //MQ连接
    @Autowired(required = false)
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer; //监听器配置

    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){ //客户端连接监听器(Connection, Channel....)
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        //factory.setMessageConverter(new Jackson2JsonMessageConverter()); //TODO: JSON
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        return factory;
    }

    /**
     * RabbitMQ发送消息的操作组件实例
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
//        cachingConnectionFactory.setPublisherConfirms(true);
        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        cachingConnectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.info("消息丢失:{}", returnedMessage);
            }
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
//            }
        });
        return rabbitTemplate;
    }

    //定义队列
    @Autowired
    private Environment env; //读取配置参数

    @Bean(name = "basicQueue")
    public Queue basicQueue(){
        return new Queue(env.getProperty("mq.basic.info.queue.name"),true);
    }

    @Bean
    public DirectExchange basicExchange(){ //一对一的交换器
        return new DirectExchange(env.getProperty("mq.basic.info.exchange.name"),true,false);
    }

    @Bean
    public Binding basicBinding(){/*队列绑定到exchange上*/
        return BindingBuilder.bind(basicQueue()).to(basicExchange())
                .with(env.getProperty("mq.basic.info.routing.key.name"));
    }




    /*888888*/
    /*创建消息模型-directExchange*/
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(env.getProperty("mq.direct.exchange.name"), true, false);
    }
        //创建队列1
        @Bean("directQueueOne")
        public Queue directQueueOne(){
        return new Queue(env.getProperty("mq.direct.queue.one.name"),true);
    }
        //队列2
        @Bean("directQueueTwo")
        public Queue directQueueTwo(){
        return new Queue(env.getProperty("mq.direct.queue.two.name"),true);
    }
    //创建绑定1
    @Bean
    public Binding directBindingOne(){
        return BindingBuilder.bind(directQueueOne()).to(directExchange()).with(env.getProperty("mq.direct.routing.key.one.name"));
    }
    //创建绑定2
    @Bean
    public Binding directBindingTwo(){
        return BindingBuilder.bind(directQueueTwo()).to(directExchange()).with(env.getProperty("mq.direct.routing.key.two.name"));
    }

/*888888*/
    /*创建消息模型-fanoutExchange*/
    /*创建队列1*/
    @Bean(name="fanoutQueueOne")
    public Queue fanoutQueueOne()
    {
        return new Queue(env.getProperty("mq.fanout.queue.one.name"));

    }
    /*创建队列2*/
    @Bean(name="fanoutQueueTwo")
    public Queue fanoutQueueTwo()
    {
        return new Queue(env.getProperty("mq.fanout.queue.two.name"));

    }
    /*创建交换机*/
    @Bean
    public FanoutExchange fanoutExchange()
    {
        return new FanoutExchange(env.getProperty("mq.fanout.exchange.name"),true,false);

    }
    /*创建绑定1*/
    @Bean
    public Binding fanoutBindingOne()
    {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }
    /*创建绑定2*/
    @Bean
    public Binding fanoutBindingTwo()
    {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }





}
