package com.jack.recycle.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class GoodsOfStation implements Serializable {
    private String uuid;

    /**
     * 基站id
     */
    private String stationId;

    /**
     * 基站法人id
     */
    private String stationLegal;

    /**
     * 经营物品类型ids
     */
    private String goodsIds;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsOfStation that = (GoodsOfStation) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(stationId, that.stationId) &&
                Objects.equals(stationLegal, that.stationLegal) &&
                Objects.equals(goodsIds, that.goodsIds);
    }
}
