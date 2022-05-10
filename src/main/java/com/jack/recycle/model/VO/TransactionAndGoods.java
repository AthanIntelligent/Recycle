package com.jack.recycle.model.VO;

import com.jack.recycle.model.PO.TransactionGoods;
import com.jack.recycle.model.Reservation;
import lombok.Data;

import java.util.List;

@Data
public class TransactionAndGoods {
    private Reservation reservation;
    private List<TransactionGoods> transactionGoodsList;
    private Double allMoney;
    private String payFlag;
}
