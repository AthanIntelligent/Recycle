package com.jack.recycle.mapper;

import com.jack.recycle.model.Factory;

public interface FactoryDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Factory record);

    int insertSelective(Factory record);

    Factory selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Factory record);

    int updateByPrimaryKey(Factory record);
}