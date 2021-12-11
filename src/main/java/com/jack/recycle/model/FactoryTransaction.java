package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * factory_transaction
 * @author 
 */
@Data
public class FactoryTransaction implements Serializable {
    private String uuid;

    /**
     * 商家id
     */
    private String factoryid;

    /**
     * 基站id
     */
    private String jzid;

    /**
     * 交易重量
     */
    private Double factoryweight;

    /**
     * 交易金额
     */
    private Double factorymoney;

    /**
     * 物品类型id
     */
    private String recycletypeid;

    private static final long serialVersionUID = 1L;
}