package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * trade_goods
 * @author 
 */
@Data
public class TradeGoods implements Serializable {
    private String uuid;

    /**
     * 交易基本信息id
     */
    private String tradeId;

    /**
     * 交易物品名称
     */
    private String goodsName;

    /**
     * 物品单价
     */
    private Double perMoney;

    /**
     * 单位
     */
    private String unit;

    /**
     * 重量/个数
     */
    private Double weight;

    /**
     * 单物品金额
     */
    private Double rmb;

    private static final long serialVersionUID = 1L;
}