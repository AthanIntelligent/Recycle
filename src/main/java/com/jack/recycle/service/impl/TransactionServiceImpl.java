package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.TransactionDao;
import com.jack.recycle.model.Transaction;
import com.jack.recycle.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;

    @Override
    public int addTransaction(Transaction transaction) {
        return transactionDao.insert(transaction);
    }

    @Override
    public List<Transaction> dirTransaction(Transaction transaction) {
        return transactionDao.selectAll(transaction);
    }

    @Override
    public List<Transaction> dirUandSTransaction(Transaction transaction) {
        return transactionDao.selectMonthAll(transaction);
    }

    @Override
    public List<Transaction> getTransactionData(String uuid) {
        return transactionDao.selectByStationId(uuid);
    }
}
