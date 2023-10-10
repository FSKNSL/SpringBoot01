package utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/*debug*/

public class RedPacketTest {
    /*定义日志*/
    private static final Logger log = (Logger) LoggerFactory.getLogger(RedPacketTest.class);

    /*二倍均值法自测方法*/
    @Test
    public void one() throws  Exception{
        Integer amount=1000;
        Integer total=10;
        List<Integer> list=RedPacketUtil.divideRedPacketage(amount,total);
        log.info("总金额={}分, 总个数={}个", amount, total);
        Integer sum=0;
        for(Integer i:list)
        {
            log.info("随机金额为:{}分,即{}元",i,new BigDecimal(i.toString()).divide(new BigDecimal(100)));
            sum+=i;
        }
        log.info("所有随机金额之和={}分",sum);

    }


}