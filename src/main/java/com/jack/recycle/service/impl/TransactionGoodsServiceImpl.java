package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.TransactionGoodsDao;
import com.jack.recycle.model.TransactionGoods;
import com.jack.recycle.service.TransactionGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionGoodsServiceImpl implements TransactionGoodsService {
    @Autowired
    private TransactionGoodsDao transactionGoodsDao;

    @Override
    public int addTransactionGoods(TransactionGoods transactionGoods) {
        return transactionGoodsDao.insert(transactionGoods);
    }
}
