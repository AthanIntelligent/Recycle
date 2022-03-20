package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * goods_type
 * @author 
 */
@Data
public class GoodsType implements Serializable {
    private String uuid;

    /**
     * 物品类型
     */
    private String goodsType;

    private static final long serialVersionUID = 1L;
}