package com.jack.recycle.model.VO;

import com.jack.recycle.model.Transaction;
import com.jack.recycle.model.TransactionGoods;
import lombok.Data;

import java.util.List;

@Data
public class TransactionAndGoods {
    private Transaction transaction;
    private List<TransactionGoods> transactionGoodsList;
}
