package com.jack.recycle.service;

import com.jack.recycle.model.TransactionGoods;

import java.util.List;

public interface TransactionGoodsService {
    int addTransactionGoods(TransactionGoods transactionGoods);

    List<TransactionGoods> dirTransactionGoods(TransactionGoods transactionGoods);
}