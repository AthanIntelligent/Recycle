package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * station_income
 * @author 
 */
@Data
public class StationIncome implements Serializable {
    private String uuid;

    /**
     * 基站id
     */
    private String jzId;

    /**
     * 日期
     */
    private String everyDay;

    /**
     * 金额
     */
    private Double everyMoney;

    private static final long serialVersionUID = 1L;
}