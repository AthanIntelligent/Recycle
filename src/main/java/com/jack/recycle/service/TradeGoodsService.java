package com.jack.recycle.service;

import com.jack.recycle.model.TradeGoods;

import java.util.List;

public interface TradeGoodsService {
    int addTradeGoods(TradeGoods tradeGoods);

    List<TradeGoods> dirTradeGoods(TradeGoods tradeGoods);
}
