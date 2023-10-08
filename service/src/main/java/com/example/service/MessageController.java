package com.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import  test.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("/test/myredis")
    public  Object  myredis()
    {
        /*向客户端写入*/
//        1.operations
        ValueOperations <String,Object> opsv= redisTemplate.opsForValue();
//        2.set/get
        opsv.set("mykey_20230919",new Message().setCode("666").setMsg("Redis..."));
//        return "ok";
//        3.get

        return opsv.get("mykey_20230919");

    }
    @RequestMapping("test/mylistredis")
    public Object myListRedis()
    {
        String key="mylistkey_20230926";
        ListOperations<String,String> opsList=redisTemplate.opsForList();
        opsList.rightPush(key,"Content 001");/*泛型:编译现象!*/
        return "OK";
    }





    @Autowired
    private RedisTemplate<String,Object> redisTemplate2;
    @RequestMapping("test/test-collection")
    public List<Message> addToCollection()
    {
        Message message=new Message();/*添加的集合对象Message*/
        message.setMsg("这是一个添加的集合");
        message.setCode("001");
       Message message2=new Message();
       message2.setCode("002");
       message2.setMsg("这是第二个集合");
        /*将对象序列化为JSON字符串*/
        ObjectMapper mapper=new ObjectMapper();
        String json=null;
        try
        {
            json=mapper.writeValueAsString(message);
            /*使用writeValueAsString将java对象转化啊为JSON格式的字符串*/
            }catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        ObjectMapper mapper3=new ObjectMapper();
        String json3=null;
        try {
            json3=mapper3.writeValueAsString(message2);
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
        }
        /*添加元素到集合中*/
        redisTemplate.opsForSet().add("myCollection",json);
        /*使用opsForSet()方法向集合中添加元素,第一个为key,第二个为要添加到集合中的元素json格式的字符串*/
        redisTemplate2.opsForSet().add("myCollection",json3);
        Set<String> set=redisTemplate.opsForSet().members("myCollection");
        /*使用RedisTemplate的opsForSet()方法获取到一个SetOperations对象,然后调用members方法获取集合中的所有元素
        * 这里的参数为集合的键值,并将返回的变量赋值为set,该变量类型为泛型的String,表示集合中的元素类型都为字符串类型*/
        List<Message>list=new ArrayList<>();
        for(String item:set)
        {
            try{
                ObjectMapper mapper2=new ObjectMapper();
                Message  msg=mapper2.readValue(item,Message.class);
                /*以上代码用过遍历Set集合中的每一元素来实现对Redis存储的JSON字符串的反序列化操作*/
                /*1.使用一个for循环来遍历Set集合中的 每一个元素*/
                /*2.创建一个ObjectMapper对象mapper实现 JSON字符串和java对象之间的转换。使用readvalue方法将当前元素item(JSON字符串)
                * 反序列化为Message对象,并将其保存在变量msg中*/
                list.add(msg);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

   return list;

        /*返回一个列表*/
    }




}

