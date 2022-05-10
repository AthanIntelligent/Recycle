package com.jack.recycle.model.PO;

import lombok.Data;

@Data
public class TransactionGoods {
    private String uuid;
    private String goodsName;
    private Double goodsPrice;
    private String goodsUnit;
    private Double goodsWeight;
    private Double amount;
}
