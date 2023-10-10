package service;

import dto.RedPacketDto;

import java.math.BigDecimal;

public interface IRedPacketService {
    String handOut(RedPacketDto dto )throws Exception;
    BigDecimal rob(Integer userId,String redId)throws  Exception;

}
