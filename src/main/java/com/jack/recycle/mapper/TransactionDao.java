package com.jack.recycle.mapper;

import com.jack.recycle.model.Transaction;

public interface TransactionDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);
}