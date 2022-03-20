package com.jack.recycle.mapper;

import com.jack.recycle.model.Transaction;

public interface TransactionDao {
    int deleteByPrimaryKey(Integer uuid);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(Integer uuid);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);
}