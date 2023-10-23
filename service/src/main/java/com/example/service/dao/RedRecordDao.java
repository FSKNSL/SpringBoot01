package com.example.service.dao;

import java.util.List;

import com.example.service.model.RedRecord;
import com.example.service.model.RedRecordExample;
import org.apache.ibatis.annotations.Param;

public interface RedRecordDao {
    long countByExample(RedRecordExample example);

    int deleteByExample(RedRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RedRecord record);

    int insertSelective(RedRecord record);

    List<RedRecord> selectByExample(RedRecordExample example);

    RedRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RedRecord record, @Param("example") RedRecordExample example);

    int updateByExample(@Param("record") RedRecord record, @Param("example") RedRecordExample example);

    int updateByPrimaryKeySelective(RedRecord record);

    int updateByPrimaryKey(RedRecord record);
}