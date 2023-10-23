package com.example.service.service.redis;

import com.example.service.service.IRedPacketService;
import com.example.service.dto.RedPacketDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.example.service.service.IRedService;
import com.example.service.utils.RedPacketUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/*红包业务处理接口-实现类*/
@Service
public class RedPacketService  implements IRedPacketService
{
    /*日志*/
    private static final Logger log= LoggerFactory.getLogger(RedPacketService.class);
    /*存储至缓存系统时定义key的前缀*/
    private static final String keyPrefix="redis:red:packet";
    /*定义Redis操作Bean组件*/
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IRedService redService;
    /*发红包*/
    @Override
    public String handOut(RedPacketDto dto)throws Exception
    {
        /*判断参数的合法性*/
        if(dto.getTotal()>0&&dto.getAmount()>0)
        {
            List<Integer> list= RedPacketUtil.divideRedPacketage(dto.getAmount(),dto.getTotal());
            /*生成红包全局唯一标识串*/
            String timestamp=String.valueOf(System.nanoTime());
            /*拼接*/
            String redId=new StringBuffer(keyPrefix).append(dto.getUserId()).append(":").append(timestamp).toString();
            redisTemplate.opsForList().leftPushAll(redId,list);
            String redTotalKey=redId+":total";
            redisTemplate.opsForValue().set(redTotalKey,dto.getTotal());
          redService.recordRedPacket(dto,redId,list);

            return  redId;
        }
        else {
            throw new Exception("系统异常-分发红包-参数不合法!");
        }
    }
    /*抢红包业务逻辑*/
   /* @Override
    public BigDecimal rob(Integer userId,String redId)throws Exception
    {
        *//*定义redis组件值的操作方法*//*
        ValueOperations valueOperations=redisTemplate.opsForValue();
        *//*首先判断用户是否已经抢过红包*//*
        *//*如果已经抢过了直接返回红包金额*//*
        Object obj=valueOperations.get(redId+userId+":rob");
        if(obj!=null)
        {
            return new BigDecimal(obj.toString());
        }
        *//*点红包业务逻辑-主要用于判断缓存系统中是否仍然有红包,即红包个数是否大于0*//*
        Boolean res=click(redId);
        if(res)
        {
            *//*res为true,则可以进入拆红包的业务逻辑处理*//*
            Object value=redisTemplate.opsForList().rightPop(redId);
            if(value!=null)
            {
                *//*当前用户抢到一个红包了,则可以进入后续的更新缓存,并将信息计入数据库*//*
                String redTotalKey=redId+":total";
                *//*更新缓存系统中剩余的红包个数,即红包个数减一*//*
                Integer currentTotal=valueOperations.get(redTotalKey)!=null?(Integer) valueOperations.get(redTotalKey):0;
                valueOperations.set(redTotalKey,currentTotal-1);
                *//*将红包金额返回给用户前，在这里将金额的单位设置为元*//*
                BigDecimal result=new BigDecimal(value.toString()).divide(new BigDecimal(100));
                *//*将抢到红包时用户的账号信息及抢到的金额等信息计入数据库*//*
                redService.recordRobRedPacket(userId,redId,new BigDecimal(value.toString()));
                *//*将当前抢到红包的用户设置进缓存系统中,表示当前用户已经抢过红包了*//*
                valueOperations.set(redId+userId+":rob",result,24L, TimeUnit.HOURS);
                *//*打印当前用户抢到红包的信息*//*
                log.info("当前用户抢到红包了:userId={} key={} 金额={}",userId,redId,result);
                *//*将结果返回*//*
                return result;
            }
        }
        *//*null表示当前用户没有抢到红包*//*
        return  null;
    }*/

    /*通过setIfAbsent()方法改造后的"抢红包业务逻辑代码"*/
    @Override
    public BigDecimal rob(Integer userId,String redId) throws  Exception
    {
        /*获取Redis的值操作组件*/
        ValueOperations valueOperations=redisTemplate.opsForValue();
        /*判断用户是否抢过红包*/
        Object obj=valueOperations.get(redId+userId+":rob");
        if(obj!=null)
        {
            return new BigDecimal(obj.toString());
        }
        Boolean res=click(redId);
        if(res)
        {
            /*上分布式锁:一个 红包没人只能抢一次随机金额,即要保证一对一关系*/
            /*构造缓存中的key*/
            final String lockKey=redId+userId+"lock";
            /*调用setIfAbsnet()方法,间接实现分布式锁*/
            Boolean lock=valueOperations.setIfAbsent(lockKey,redId);
            /*设定该分布锁的过期时间为24h*/
            redisTemplate.expire(lockKey,24L,TimeUnit.HOURS);
            try{
                /*表示该线程获取到了该分布式锁*/
                if(lock)
                {
                    Object value=redisTemplate.opsForList().rightPop(redId);
                    if(value!=null)
                    {
                /*当前用户抢到一个红包了,则可以进入后续的更新缓存,并将信息计入数据库*/
                        String redTotalKey=redId+":total";
                /*更新缓存系统中剩余的红包个数,即红包个数减一*/
                        Integer currentTotal=valueOperations.get(redTotalKey)!=null?(Integer) valueOperations.get(redTotalKey):0;
                        valueOperations.set(redTotalKey,currentTotal-1);
                /*将红包金额返回给用户前，在这里将金额的单位设置为元*/
                        BigDecimal result=new BigDecimal(value.toString()).divide(new BigDecimal(100));
                /*将抢到红包时用户的账号信息及抢到的金额等信息计入数据库*/
                        redService.recordRobRedPacket(userId,redId,new BigDecimal(value.toString()));
                /*将当前抢到红包的用户设置进缓存系统中,表示当前用户已经抢过红包了*/
                        valueOperations.set(redId+userId+":rob",result,24L, TimeUnit.HOURS);
                /*打印当前用户抢到红包的信息*/
                        log.info("当前用户抢到红包了:userId={} key={} 金额={}",userId,redId,result);
                /*将结果返回*/
                        return result;
                }}
            }catch(Exception e)
            {
                throw new Exception("系统异常-抢红包-加分布式锁失败!");
            }


        }
        return null;
    }



/*点红包的业务处理逻辑,如果返回true,则表示缓存系统还有红包,否则红包被抢完了*/
    private Boolean click(String redId)throws  Exception
    {
        ValueOperations valueOperations=redisTemplate.opsForValue();
        /*定义用于查询缓存系统中红包剩余个数的key*/
        String redTotalKey=redId+":total";
        /*获取缓存系统Redis中红包剩余个数*/
        Object total=valueOperations.get(redTotalKey);
        /*判断红包剩余个数是否大于0,如果大于0则返回true*/
        if(total!=null&&Integer.valueOf(total.toString())>0)
        {
            return true;
        }
        /*表示没有红包可以抢了*/
        return false;
    }


}
