package com.jack.recycle.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class TransactionGoods implements Serializable {
    private String uuid;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 金额
     */
    private Double money;

    /**
     * 物品id
     */
    private String goodsId;

    /**
     * 交易id
     */
    private String transactionId;

    private static final long serialVersionUID = 1L;
}
