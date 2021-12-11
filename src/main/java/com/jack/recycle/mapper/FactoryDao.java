package com.jack.recycle.mapper;

import com.jack.recycle.model.Factory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FactoryDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Factory record);

    int insertSelective(Factory record);

    Factory selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Factory record);

    int updateByPrimaryKey(Factory record);
}