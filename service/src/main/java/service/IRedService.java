package service;

import dto.RedPacketDto;

import java.math.BigDecimal;
import java.util.List;

/*红包业务逻辑处理数据记录接口-异步实现*/
public interface IRedService {
    void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list)throws  Exception;
    void recordRobRedPacket(Integer userId, String redId, BigDecimal amount)throws Exception;
}
