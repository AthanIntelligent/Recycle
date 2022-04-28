package com.jack.recycle.mapper;


import com.jack.recycle.model.GoodsOfStation;

public interface GoodsOfStationDao {
    int insert(GoodsOfStation typeOfStation);

    GoodsOfStation selectByStationLegal(String stationLegalId);

    int updateByPrimaryKeySelective(GoodsOfStation typeOfStation);

    GoodsOfStation selectByStationId(String stationId);
}
