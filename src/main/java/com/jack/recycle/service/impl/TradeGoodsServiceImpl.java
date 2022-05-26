package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.TradeGoodsDao;
import com.jack.recycle.model.TradeGoods;
import com.jack.recycle.service.TradeGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeGoodsServiceImpl implements TradeGoodsService {
    @Autowired
    TradeGoodsDao tradeGoodsDao;

    @Override
    public int addTradeGoods(TradeGoods tradeGoods) {
        return tradeGoodsDao.insert(tradeGoods);
    }

    @Override
    public List<TradeGoods> dirTradeGoods(TradeGoods tradeGoods) {
        return tradeGoodsDao.selectAll(tradeGoods);
    }

    @Override
    public List<TradeGoods> dirByTradeIds(List<String> tradeIds) {
        return tradeGoodsDao.selectByTradeIds(tradeIds);
    }
}
