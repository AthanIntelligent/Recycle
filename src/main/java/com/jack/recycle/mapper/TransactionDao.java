package com.jack.recycle.mapper;

import com.jack.recycle.model.Transaction;

import java.util.List;

public interface TransactionDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(String uuid);

    List<Transaction> selectAll(Transaction record);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

    List<Transaction> selectMonthAll(Transaction transaction);
}