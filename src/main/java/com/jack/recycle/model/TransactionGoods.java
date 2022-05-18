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
     * 单价
     */
    private Double money;

    /**
     * 单位
     */
    private String unit;

    /**
     * 重量/个数 * 单价
     */
    private Double rmb;

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
