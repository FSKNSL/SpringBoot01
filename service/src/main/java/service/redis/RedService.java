package service.redis;

import dao.RedDetailDao;
import dao.RedRecordDao;
import dao.RedRobRecordDao;
import dto.RedPacketDto;
import model.RedDetail;
import model.RedRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.IRedService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*红包业务逻辑处理数据记录接口-接口实现类*/
@Service
@EnableAsync
public class RedService implements IRedService {
    /*日志*/
    private static  final Logger log= LoggerFactory.getLogger(RedService.class);
    /*发红包时全局唯一标识串等信息操作接口Mapper*/
    @Autowired
    private RedRecordDao redRecordDao;
    @Autowired
    private RedDetailDao redDetailDao;
    @Autowired
    private RedRobRecordDao redRobRecordDao;


    /*发红包记录*/
    @Override
    @Async
    @Transactional(rollbackFor=Exception.class)
    public void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception {
        /*定义实体类对象*/
        RedRecord  redRecord=new RedRecord();
        /*设置字段的取值信息*/
        redRecord.setUserId(dto.getUserId());
        redRecord.setRedPacket(redId);
        redRecord.setTotal(dto.getTotal());
        redRecord.setAmount(BigDecimal.valueOf(dto.getAmount()));
        redRecord.setCreateTime(new Date());
        /*将对象信息插入数据库*/
     redRecordDao.insertSelective(redRecord);



        RedDetail detail;
        for(Integer i:list)
        {
            detail=new RedDetail();
            detail.setRecordId(redRecord.getId());
            detail.setAmount(BigDecimal.valueOf(i));
            detail.setCreateTime(new Date());
            /*将对象信息插入数据库*/
            redDetailDao.insertSelective(detail);
        }

    }

    /*抢红包记录*/
    @Override
    @Async
    public void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception {

    }
}
