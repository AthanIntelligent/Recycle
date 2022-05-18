package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.TransactionGoodsDao;
import com.jack.recycle.model.TransactionGoods;
import com.jack.recycle.service.TransactionGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionGoodsServiceImpl implements TransactionGoodsService {
    @Autowired
    private TransactionGoodsDao transactionGoodsDao;

    @Override
    public int addTransactionGoods(TransactionGoods transactionGoods) {
        return transactionGoodsDao.insert(transactionGoods);
    }

    @Override
    public List<TransactionGoods> dirTransactionGoods(TransactionGoods transactionGoods) {
        return transactionGoodsDao.selectAll(transactionGoods);
    }

    @Override
    public List<TransactionGoods> dirByTransactionIds(List<String> tranIdsList) {
        return transactionGoodsDao.selectByTransactionIds(tranIdsList);
    }
}
