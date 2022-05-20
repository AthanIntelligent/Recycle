package com.jack.recycle.mapper;

import com.jack.recycle.model.Trade;

public interface TradeDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Trade record);

    int insertSelective(Trade record);

    Trade selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKey(Trade record);
}