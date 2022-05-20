package com.jack.recycle.mapper;

import com.jack.recycle.model.TradeGoods;

public interface TradeGoodsDao {
    int deleteByPrimaryKey(String uuid);

    int insert(TradeGoods record);

    int insertSelective(TradeGoods record);

    TradeGoods selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(TradeGoods record);

    int updateByPrimaryKey(TradeGoods record);
}