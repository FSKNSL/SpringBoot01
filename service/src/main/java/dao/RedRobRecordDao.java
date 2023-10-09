package dao;

import java.util.List;
import model.RedRobRecord;
import model.RedRobRecordExample;
import org.apache.ibatis.annotations.Param;

public interface RedRobRecordDao {
    long countByExample(RedRobRecordExample example);

    int deleteByExample(RedRobRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RedRobRecord record);

    int insertSelective(RedRobRecord record);

    List<RedRobRecord> selectByExample(RedRobRecordExample example);

    RedRobRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RedRobRecord record, @Param("example") RedRobRecordExample example);

    int updateByExample(@Param("record") RedRobRecord record, @Param("example") RedRobRecordExample example);

    int updateByPrimaryKeySelective(RedRobRecord record);

    int updateByPrimaryKey(RedRobRecord record);
}