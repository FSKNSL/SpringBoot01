package com.example.service.dao;

import java.util.List;
import com.example.service.model.RedDetail;
import com.example.service.model.RedDetailExample;
import org.apache.ibatis.annotations.Param;

public interface RedDetailDao {
    long countByExample(RedDetailExample example);

    int deleteByExample(RedDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RedDetail record);

    int insertSelective(RedDetail record);

    List<RedDetail> selectByExample(RedDetailExample example);

    RedDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RedDetail record, @Param("example") RedDetailExample example);

    int updateByExample(@Param("record") RedDetail record, @Param("example") RedDetailExample example);

    int updateByPrimaryKeySelective(RedDetail record);

    int updateByPrimaryKey(RedDetail record);
}