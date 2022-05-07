package com.jack.recycle.service;

import com.jack.recycle.model.Transaction;

import java.util.List;

public interface TransactionService {
    int addTransaction(Transaction transaction);
    List<Transaction> dirTransaction(Transaction transaction);
}
