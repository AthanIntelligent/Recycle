package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * recycle_item_type
 * @author 
 */
@Data
public class RecycleItemType implements Serializable {
    private String uuid;

    /**
     * 回收物品类型
     */
    private String recycleType;

    /**
     * 回收物品名称
     */
    private String recycleName;

    /**
     * 物品详细介绍
     */
    private String recycleDetail;

    /**
     * 单价
     */
    private Double perMoney;

    private static final long serialVersionUID = 1L;
}