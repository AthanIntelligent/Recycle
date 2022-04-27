package com.jack.recycle.service;

import com.jack.recycle.model.GoodsOfStation;

public interface GoodsOfStationService {
    int addGoodsOfStation(GoodsOfStation goodsOfStation);

    GoodsOfStation getGoodsOfStation(String stationLegalId);

    int updGoodsOfStation(GoodsOfStation goodsOfStation);
}
