package com.jack.recycle.service;

import com.jack.recycle.model.Trade;

import java.util.List;

public interface TradeService {
    int addTrade(Trade trade);

    List<Trade> dirTrade(Trade trade);

    List<Trade> getTradeData(String uuid);
}
