package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * trade
 * @author 
 */
@Data
public class Trade implements Serializable {
    private String uuid;

    /**
     * 厂商id
     */
    private String factoryId;

    /**
     * 基站id
     */
    private String stationId;

    /**
     * 交易总金额
     */
    private Double allMoney;

    /**
     * 交易时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;
}