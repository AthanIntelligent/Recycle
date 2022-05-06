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
    private String transactionTime;

    /**
     * 物品ids
     */
    private String transactionGoodsId;

    /**
     * 基站人员id
     */
    private String stationLegal;

    private static final long serialVersionUID = 1L;
}