package com.jack.recycle.service;

import com.jack.recycle.model.GoodsType;

import java.util.List;

public interface GoodsTypeService {
    List<GoodsType> dirGoodsType();

    int delGoodsType(String uuid);

    int addGoodsType(GoodsType goodsType);

    String getGoodsTypeUuid(String goodsType);

    String getGoodsTypeName(String goodsTypeUuid);
}
