package com.jack.recycle.mapper;

import com.jack.recycle.model.FactoryTransaction;

public interface FactoryTransactionDao {
    int deleteByPrimaryKey(String uuid);

    int insert(FactoryTransaction record);

    int insertSelective(FactoryTransaction record);

    FactoryTransaction selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(FactoryTransaction record);

    int updateByPrimaryKey(FactoryTransaction record);
}