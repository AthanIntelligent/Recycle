package com.jack.recycle.service;

import com.jack.recycle.model.GoodsOfStation;

public interface GoodsOfStationService {
    int addGoodsOfStation(GoodsOfStation goodsOfStation);

    GoodsOfStation getGoodsOfStationByStationLegal(String stationLegalId);

    int updGoodsOfStation(GoodsOfStation goodsOfStation);

    GoodsOfStation getGoodsOfStationByStationId (String stationId);
}
