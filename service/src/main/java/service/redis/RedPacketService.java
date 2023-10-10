package service.redis;

import dto.RedPacketDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import service.IRedPacketService;
import service.IRedService;
import utils.RedPacketUtil;

import java.math.BigDecimal;
import java.util.List;

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
    @Override
    public BigDecimal rob(Integer userId,String redId)throws Exception
    {
        return null;
    }




}
