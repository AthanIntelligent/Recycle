package com.jack.recycle.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transaction
 * @author 
 */
@Data
public class Transaction implements Serializable {
    private String uuid;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 金额
     */
    private String money;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 基站id
     */
    private String stationId;

    /**
     * 回收时间
     */
    private Date transactionTime;

    /**
     * 物品id
     */
    private String goodsId;

    /**
     * 基站人员id
     */
    private String stationUserId;

    private static final long serialVersionUID = 1L;
}