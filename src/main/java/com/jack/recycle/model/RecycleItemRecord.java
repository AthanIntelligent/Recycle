package com.jack.recycle.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * recycle_item_record
 * @author 
 */
@Data
public class RecycleItemRecord implements Serializable {
    private String uuid;

    /**
     * 回收类型id
     */
    private String recycleTypeId;

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
    private String jzId;

    /**
     * 回收时间
     */
    private Date recycleTime;

    private static final long serialVersionUID = 1L;
}