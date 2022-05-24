package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.TradeDao;
import com.jack.recycle.model.Trade;
import com.jack.recycle.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private TradeDao tradeDao;

    @Override
    public int addTrade(Trade trade) {
        return tradeDao.insert(trade);
    }

    @Override
    public List<Trade> dirTrade(Trade trade) {
        return tradeDao.selectAll(trade);
    }

    @Override
    public List<Trade> getTradeData(String uuid) {
        return tradeDao.selectByStationId(uuid);
    }
}
