package com.jack.recycle.mapper;

import com.jack.recycle.model.TransactionGoods;

import java.util.List;

public interface TransactionGoodsDao {
    int insert(TransactionGoods transactionGoods);

    List<TransactionGoods> selectAll(TransactionGoods transactionGoods);

    List<TransactionGoods> selectByTransactionIds(List<String> idList);

    int updateByPrimaryKeySelective(TransactionGoods transactionGoods);
}
