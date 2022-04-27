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
     * 新旧程度备注
     */
    private String remark;

    /**
     * 物品图片
     */
    private String pic;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 操作栏是否显示；不是数据库字段，是有查询列表的时候用到
     */
    private Boolean isVisible;

    private static final long serialVersionUID = 1L;
}