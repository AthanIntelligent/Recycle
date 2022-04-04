package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * goods
 * @author 
 */
@Data
public class Goods implements Serializable {
    private String uuid;

    /**
     * 回收物品类型
     */
    private String goodsType;

    /**
     * 回收物品名称
     */
    private String goodsName;

    /**
     * 物品详细介绍
     */
    private String recycleDetail;

    /**
     * 单价
     */
    private Double perMoney;

    /**
     * 新旧程度
     */
    private String remark;

    /**
     * 物品图片
     */
    private String pic;

    private static final long serialVersionUID = 1L;
}