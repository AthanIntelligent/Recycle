package com.jack.recycle.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeOfStation implements Serializable {
    private String uuid;

    /**
     * 基站id
     */
    private String stationId;

    /**
     * 经营物品类型ids
     */
    private String goodsIds;

    private static final long serialVersionUID = 1L;
}
