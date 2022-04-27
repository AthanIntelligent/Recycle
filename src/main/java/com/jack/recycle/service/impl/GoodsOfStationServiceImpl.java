package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.GoodsOfStationDao;
import com.jack.recycle.model.GoodsOfStation;
import com.jack.recycle.service.GoodsOfStationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class GoodsOfStationServiceImpl implements GoodsOfStationService {
    @Autowired
    GoodsOfStationDao goodsOfStationDao;

    @Override
    public int addGoodsOfStation(GoodsOfStation typeOfStation) {
        int insert = goodsOfStationDao.insert(typeOfStation);
        return insert;
    }

    @Override
    public GoodsOfStation getGoodsOfStation(String stationLegalId) {
        GoodsOfStation goodsOfStation = goodsOfStationDao.selectByStationLegal(stationLegalId);
        return goodsOfStation;
    }

    @Override
    public int updGoodsOfStation(GoodsOfStation goodsOfStation) {
        return goodsOfStationDao.updateByPrimaryKeySelective(goodsOfStation);
    }
}
